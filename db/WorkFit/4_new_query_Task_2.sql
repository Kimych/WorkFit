SELECT max(workfit.days_of_work.WORKLOG) AS worked,
	(max(workfit.days_of_work.WORKLOG)-max(workfit.days_of_work.AKTUAL_WORKED_DAYS)*8) AS aberration,
	(max(workfit.days_of_work.WORKLOG)-  workfit.months.WORKING_DAYS_COUNT*8) AS remaining
 FROM workfit.days_of_work INNER JOIN workfit.months ON workfit.days_of_work.MONTH_ID = workfit.months.MONTH_ID
WHERE  workfit.days_of_work.MONTH_ID = 1 and workfit.days_of_work.WORKER_ID = 3;