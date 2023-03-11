echo "Go to 'Service Admin' > 'Plugins' > 'Grafana OnCall' > Configuration"
docker compose run engine python manage.py issue_invite_for_the_frontend --override
echo "Your 'OnCall backend URL': http://engine:8080"
echo "Your 'Grafana URL': http://grafana:3000"
