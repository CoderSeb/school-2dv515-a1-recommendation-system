# Production notes for current and future use


## Heroku

Live @ https://sebs-assignment-1-backend.herokuapp.com/api

### Docker
Start docker desktop

### Login
heroku login
heroku container:login

### Build & Push container
heroku container:push web -a sebs-assignment-1-backend

### Take it live
heroku container:release web -a sebs-assignment-1-backend