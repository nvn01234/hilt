docker compose exec -it superset-webserver
          superset fab create-admin
          --username=admin
          --firstname=Ad
          --lastname=Min
          --email=ad.min@gmail.com
          --password=SuperSecr3t

docker compose exec -it superset-webserver superset init
