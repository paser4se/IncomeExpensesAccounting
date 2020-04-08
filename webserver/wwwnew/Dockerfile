FROM node:10

COPY package.json .

RUN npm install --unsafe-perm

COPY . .

RUN cp -avr node_modules/devextreme/dist/css/icons/ src/themes/generated && sed -i 's/#FF5722/#288B9E/g' src/themes/generated/variables.base.scss && sed -i 's/#be2d00/#206f7e/g' src/themes/generated/variables.base.scss && sed -i 's/#FF5722/#288B9E/g' src/themes/generated/variables.additional.scss && sed -i 's/#ff5722/#288B9E/g' src/themes/generated/theme.base.css && sed -i 's/#f63b00/#206f7e/g' src/themes/generated/theme.base.css && sed -i 's/#be2d00/#206f7e/g' src/themes/generated/theme.base.css && sed -i 's/#ff5722/#288B9E/g' src/themes/generated/theme.additional.css && sed -i 's/#f63b00/#206f7e/g' src/themes/generated/theme.additional.css && sed -i 's/#be2d00/#206f7e/g' src/themes/generated/theme.additional.css

EXPOSE 8080

CMD [ "npm", "run", "serve" ]
# CMD tail -f /dev/null