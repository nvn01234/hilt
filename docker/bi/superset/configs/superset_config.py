import logging
from cachelib.redis import RedisCache
from celery.schedules import crontab
from flask_appbuilder.security.manager import AUTH_OAUTH
import os
import json

logging.basicConfig(level=logging.INFO, format='[%(asctime)s] [%(levelname)-5s] %(name)-15s:%(lineno)d: %(message)s')

def get_env_variable(var_name, default=None):
    """Get the environment variable or raise exception."""
    try:
        return os.environ[var_name]
    except KeyError:
        if default is not None:
            return default
        else:
            error_msg = f'The environment variable {var_name} was missing, abort...'
            raise EnvironmentError(error_msg)

#superset http, port, address (for cache warmup only)
SUPERSET_WEBSERVER_PROTOCOL = 'http'
SUPERSET_WEBSERVER_ADDRESS = 'superset-webserver'
SUPERSET_WEBSERVER_PORT = 8088
ENABLE_PROXY_FIX = True

postgres_username = get_env_variable('POSTGRES_USERNAME')
postgres_password = get_env_variable('POSTGRES_PASSWORD')
postgres_host = 'postgresql'
postgres_db = 'superset'

SQLALCHEMY_DATABASE_URI = f'postgresql://{postgres_username}:{postgres_password}@{postgres_host}:5432/{postgres_db}'
WEBDRIVER_BASEURL = 'http://superset-webserver:8088/'
SECRET_KEY = get_env_variable('SECRET_KEY') # openssl rand -base64 42

# ===== Celery =====
class CeleryConfig:
    BROKER_URL = 'redis://redis:6379/0' # _kombu.binding.
    CELERY_IMPORTS = (
        'superset.sql_lab',
        'superset.tasks',
        'superset.tasks.thumbnails',
    )
    CELERY_RESULT_BACKEND = 'redis://redis:6379/1'  # celery-task-meta-
    CELERYD_LOG_LEVEL = 'INFO'
    CELERYD_PREFETCH_MULTIPLIER = 10
    CELERY_ACKS_LATE = True
    CELERY_ANNOTATIONS = {
        'sql_lab.get_sql_results': {
            'rate_limit': '100/s'
        },
        'email_reports.send': {
            'rate_limit': '1/s',
            'time_limit': 600,
            'soft_time_limit': 600,
            'ignore_result': True,
        },
    }
    CELERYBEAT_SCHEDULE = {
        'email_reports.schedule_hourly': {
            'task': 'email_reports.schedule_hourly',
            'schedule': crontab(minute=1, hour='*'),  # hourly
        },
        'cache-warmup-hourly': {
            'task': 'cache-warmup',
            'schedule': crontab(minute=0, hour='*'),  # hourly
            'kwargs': {
                'strategy_name': 'top_n_dashboards',
                'top_n': 5,
                'since': '7 days ago',
            },
        },
        'reports.scheduler': {
            'task': 'reports.scheduler',
            'schedule': crontab(minute='*', hour='*'),  # every minute
        },
        'reports.prune_log': {
            'task': 'reports.prune_log',
            'schedule': crontab(minute=0, hour=0),  # daily
        },
    }


CELERY_CONFIG = CeleryConfig
RESULTS_BACKEND = RedisCache(host='redis', port=6379, db=2, key_prefix='superset.results.')

# ===== Caching =====
# https://flask-caching.readthedocs.io/en/latest/#configuring-flask-caching

# Caching for Superset's own metadata
CACHE_CONFIG = {
    'CACHE_TYPE': 'redis',
    'CACHE_DEFAULT_TIMEOUT': 60 * 60 * 24,  # 1 day
    'CACHE_KEY_PREFIX': 'superset.metadata.',
    'CACHE_REDIS_URL': 'redis://redis:6379/3',
}

# Caching for charting data queried from datasources
DATA_CACHE_CONFIG = {
    'CACHE_TYPE': 'redis',
    'CACHE_DEFAULT_TIMEOUT': 60 * 60 * 24,  # 1 day
    'CACHE_KEY_PREFIX': 'superset.query.',
    'CACHE_REDIS_URL': 'redis://redis:6379/4',
}

THUMBNAIL_CACHE_CONFIG = {
    'CACHE_TYPE': 'redis',
    'CACHE_DEFAULT_TIMEOUT': 60 * 60 * 24,  # 1 day
    'CACHE_KEY_PREFIX': 'superset.thumbnail.',
    'CACHE_REDIS_URL': 'redis://redis:6379/5',
}

FILTER_STATE_CACHE_CONFIG = {
    'CACHE_TYPE': 'redis',
    'CACHE_DEFAULT_TIMEOUT': 60 * 60 * 24,  # 1 day
    'CACHE_KEY_PREFIX': 'superset.filter_state.',
    'CACHE_REDIS_URL': 'redis://redis:6379/6',
    'REFRESH_TIMEOUT_ON_RETRIEVAL': True,
}

EXPLORE_FORM_DATA_CACHE_CONFIG = {
    'CACHE_TYPE': 'redis',
    'CACHE_DEFAULT_TIMEOUT': 60 * 60 * 24,  # 1 day
    'CACHE_KEY_PREFIX': 'superset.explore_form_data.',
    'CACHE_REDIS_URL': 'redis://redis:6379/7',
    'REFRESH_TIMEOUT_ON_RETRIEVAL': True,
}

# ===== Optional Feature =====
FEATURE_FLAGS = {
    'THUMBNAILS': True,
    'ROW_LEVEL_SECURITY': True,
    'ALERT_REPORTS': True,
    'DASHBOARD_RBAC': True,
    'DASHBOARD_CROSS_FILTERS': True,
    'DASHBOARD_NATIVE_FILTERS': True,
    'DASHBOARD_NATIVE_FILTERS_SET': True,
    'DASHBOARD_FILTERS_EXPERIMENTAL': True,
}

from flask_appbuilder.security.manager import AUTH_DB
from superset.security import SupersetSecurityManager

# The default user self registration role
AUTH_TYPE = AUTH_DB
AUTH_USER_REGISTRATION = True
AUTH_USER_REGISTRATION_ROLE = 'Public'

class SimpleSecurityManager(SupersetSecurityManager):
    def __init__(self, appbuilder):
        super(SimpleSecurityManager, self).__init__(appbuilder)

CUSTOM_SECURITY_MANAGER = SimpleSecurityManager

# ==== Upload Data Folder ===
UPLOAD_FOLDER = '/opt/superset/static/uploads/'
IMG_UPLOAD_FOLDER = '/opt/superset/static/uploads/'

# ==== Event loging ===
from superset.utils.log import DBEventLogger
import requests

EVENT_REST_URL = 'http://postgrest:3000/logs'
class RestEventLogger(DBEventLogger):
    def log(self, user_id, action, *args, **kwargs):
        records = kwargs.get('records', list())
        dashboard_id = kwargs.get('dashboard_id')
        slice_id = kwargs.get('slice_id')
        duration_ms = kwargs.get('duration_ms')
        referrer = kwargs.get('referrer')

        for record in records:
            event = dict(
                action=action,
                json=record,
                dashboard_id=dashboard_id,
                slice_id=slice_id,
                duration_ms=duration_ms,
                referrer=referrer,
                user_id=user_id
            )
            requests.post(EVENT_REST_URL, json = event)

EVENT_LOGGER = RestEventLogger()
