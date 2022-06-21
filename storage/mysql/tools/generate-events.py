import random
from faker import Faker
import mysql.connector
from mysql.connector.connection_cext import CMySQLConnection as Connection
from typing import Any, Dict, List

def insert(connection: Connection, table: str, column_values: Dict[str, Any]):
    sql = f"INSERT INTO {table}({','.join(column_values.keys())}) values ({','.join(['%']*len(column_values))})"
    values = column_values.values()
    cursor = connection.cursor()
    try:
        cursor.execute(sql, values)
        cursor.commit()
    except:
        connection.rollback()

def update(connection: Connection, table: str, column_values: Dict[str, Any]):
    column_values_assignment = list(map(lambda kv: f'{kv[0]}=%', column_values.keys()))
    sql = f"UPDATE {table} set ({','.join(column_values_assignment)})"
    values = column_values.values()
    cursor = connection.cursor()
    try:
        cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        cursor.execute(sql, values)
        cursor.commit()
    except:
        connection.rollback()

def delete(connection: Connection, table: str, id_columns: List[str], id_values: List[List[Any]]):
    values = list(map(lambda id_value: f"({','.join(map(str, id_value))})", id_values))
    sql = f"DELETE FROM {table} WHERE ({','.join(id_columns)}) IN ({','.join(values)})"
    cursor = connection.cursor()
    try:
        cursor.execute("SET FOREIGN_KEY_CHECKS=0")
        cursor.execute(sql, values)
        cursor.commit()
    except:
        connection.rollback()

def random_select(connection: Connection, table: str, n_rows: int = 10, select_column: List[str] = ['*']) -> List[List[Any]]:
    sql = f"SELECT {','.join(select_column)} FROM {table} ORDER BY RAND() LIMIT {n_rows}"
    cursor = connection.cursor()
    try:
        cursor.execute(sql)
        return cursor.fetchall()
    except:
        return None

def get_column_dtype(connection: Connection, table: str) -> Dict[str, str]:
    sql = f"select COLUMN_NAME, DATA_TYPE from information_schema.columns where table_name = '{table}'"
    cursor = connection.cursor()
    try:
        cursor.execute(sql)
        rs = cursor.fetchall()
        print(rs)
        return dict(map(lambda row: (row[0], row[1]), rs))
    except:
        return None

def random_values(column_type: Dict[str, str], n_rows = 10):
    lorem = 'It is a long established fact that a reader will be distracted by the readable'
    generator = {
        'smallint': random.randrange(0, 255),
        'varchar': lorem[0:random.randint(1, len(lorem) - 1)],
        'text': lorem[0:random.randint(1, len(lorem) - 1)]
    }
    columns = []
    for _ in range(n_rows):
        columns.append(dict(map(lambda kv: (kv[0], generator.get(kv[1])), column_type.items())))

    print(columns)
    return columns


if __name__ == "__main__":
    connection = mysql.connector.connect(
        host="localhost",
        user="root",
        password="SuperSecr3t",
        database="sakila"
    )
    
    print(random_values(get_column_dtype(connection, 'film_text')))
