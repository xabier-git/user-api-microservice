#CLonar repo
git clone https://github.com/xabier-git/user-api-microservice.git

#Compilar sin test
#./gradlew clean build -x test

#Ejecutar app en puerto 8080
./gradlew bootRun

#Abrir la app de escritorio Postman y cargar el documento User-API-Microservice.postman_collection.json

#Ejecutar los distintos llamadas Rest

#Login
Metodo(POST) 
localhost:8080/login/api/v1?username=admin&password=1234
Body Response:  token-jwt

#AddUser
URL: localhost:8080/users/api/v1
Método: POST
Header : Authoritazion : Bearer token.jwt
Response Body: [mensaje exito]

#getAllUser
URL: localhost:8080/users/api/v1
Método: GET
Header : Authoritazion : Bearer token.jwt
Body Response : {Listado JSON con todos los usuarios]

#updateUser
URL: localhost:8080/users/api/v1/:id
Método: GET
Header : Authoritazion : Bearer token.jwt
Bode Response: [Usuario modificado]




#Utilitarios en consola linux

# Loguear y obtener token-jwt
curl -v -X POST "http://localhost:8080/login/api/v1?username=admin&password=1234"

# agregar un usuario
curl -v -X POST "http://localhost:8080/users/api/v1" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer ´{poner aqui el token-jwt}" \
-d '{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "Hun77eer2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}'

#ejemplo obtener usuarios con un token real

curl -v "http://localhost:8080/users/api/v1" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTY0MTY4MiwiZXhwIjoxNzUxNjQ1MjgyfQ.etMVfjVueFYzlMh2um2MzqoDE2TRW0TW9Q2DZG2nzZ"