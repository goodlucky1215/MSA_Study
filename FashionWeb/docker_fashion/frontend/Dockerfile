FROM node:18-alpine

MAINTAINER "lucky <lucky@com>"

LABEL perpose = 'fashionweb frontend' \
      version = '1.0' \
      description = 'fashionweb frontend'

RUN npm install \
        react-scripts@3.0.1 -g

WORKDIR /react_app

CMD ["npm","start"]
