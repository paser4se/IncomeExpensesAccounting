FROM node:8

RUN npm init --y
RUN npm install @vue/cli
RUN npm install devextreme-cli

# install simple http server for serving static content
RUN npm install http-server

# make the 'app' folder the current working directory
WORKDIR /app

# copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

# install project dependencies
RUN npm install --unsafe-perm
RUN npm rebuild node-sass

# copy project files and folders to the current working directory (i.e. 'app' folder)
# COPY . .

# build app for production with minification
# RUN npm run build

EXPOSE 8080
VOLUME /app
CMD [ "npm", "run", "serve" ]
