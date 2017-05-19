FROM python:2.7.13-alpine

RUN mkdir /app
COPY libs/* /app/

WORKDIR /app

ENTRYPOINT ["python", "-m", "SimpleHTTPServer"]
