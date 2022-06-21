import random
import time
import mysql.connector             
from mysql.connector import Error  
from faker import Faker

Faker.seed(33422)
fake = Faker()

connection = mysql.connector.connect(
    host="localhost",
    user="root",
    password="SuperSecr3t",
    database="sakila"
)
    

while(True):
    try:
        cursor = connection.cursor()
        row = [random.randint(-32768, 32767), 'shjidfvbjsdhfbvsdfv', 'sdfivsduiofbvsydhfbvdfv']
        print(f"Insert 1 row..... {row}")
        cursor.execute(f"INSERT INTO `film_text` (film_id, title, description) VALUES ({row[0]}, '{row[1]}', '{row[2]}');")
        connection.commit()
        time.sleep(0.2)
    except:
        pass