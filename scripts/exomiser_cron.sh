python ~/cron/exomiser_cron.py --since=`date --date="1 day ago" +%Y-%m-%d` /var/lib/phenotips /var/lib/exomiser/exomiser-cli-6.0.0/exomiser-cli-6.0.0.jar /var/lib/exomiser/exomiser.credentials &> ~/cron/exomiser_cron_logs/`date +%Y-%m-%d.%H%M%S`.log
