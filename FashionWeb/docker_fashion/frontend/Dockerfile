FROM node:18-alpine

MAINTAINER "lucky <lucky@com>"

LABEL perpose = 'fashionweb frontend' \
      version = '1.0' \
      description = 'fashionweb frontend'

WORKDIR /react_app
COPY ./user-front ./
RUN npm install

CMD ["npm","start"]
