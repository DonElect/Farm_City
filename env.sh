#!/bin/bash

export SPRING_PROFILE="docker"

export DATASOURCE_URL="jdbc:postgresql://dpg-cr15tjbtq21c73cpd2q0-a/farm_city_db"
export DATASOURCE_USERNAME="farm_city_db_user"
export DATASOURCE_PASSWORD="xiCCqtOre4tVikh4hRN4NFqhYCEzmcnx"

export EMAIL_PASSWORD="aecd mxjx bfrj sgwy"
export EMAIL_USERNAME="marketdefarmers@gmail.com"
export EMAIL_PORT=587
export EMAIL_HOST="smtp.gmail.com"
export EMAIL_SMTP_AUTH="true"
export EMAIL_START_TLS="true"

export DEFAULT_EMAIL_SENDER="marketdefarmers@gmail.com"
export EXPIRED_OTPS_CRON="0 0 */1 * * *"

