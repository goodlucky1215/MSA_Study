FROM python:3.8-alpine
RUN apk update && \
        apk add --no-cache \
        bash
RUN apk add --update build-base python3-dev py-pip

ENV LIBRARY_PATH=/lib:/usr/lib
ENV FLASK_APP=py_app
ENV FLASK_ENV=development

EXPOSE 9000

WORKDIR /py_app
COPY ./app/ .

RUN pip install -r requirements.txt

ENTRYPOINT ["python"]
CMD ["py_app.py"]
