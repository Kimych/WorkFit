SELECT max(workfit.days_of_work.WORKLOG) AS worked, (max(workfit.days_of_work.WORKLOG)-max(workfit.days_of_work.AKTUAL_WORKED_DAYS)*8) AS aberration
 FROM workfit.days_of_work
WHERE  MONTH_ID = 1 and WORKER_ID = 3;