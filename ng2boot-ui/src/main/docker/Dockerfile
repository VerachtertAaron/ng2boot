FROM python:2.7.13-alpine

RUN mkdir /app
COPY libs/ng2boot-ui.war /app/ng2boot-ui.war
RUN cd /app; unzip ng2boot-ui.war
WORKDIR /app

ENTRYPOINT ["python", "-m", "SimpleHTTPServer"]
