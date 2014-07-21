SELECT max(workfit.days_of_work.WORKLOG) AS worked,
	(max(workfit.days_of_work.WORKLOG) + workfit.days_of_work.BONUS_TIME - max(workfit.days_of_work.AKTUAL_WORKED_DAYS + workfit.spent_holiday.COUNT_DAYS)*8) AS aberration,
		workfit.spent_holiday.COUNT_DAYS AS spent_holidays		
FROM workfit.days_of_work INNER JOIN workfit.worker ON workfit.days_of_work.WORKER_ID = workfit.worker.WORKER_ID
							INNER JOIN workfit.spent_holiday ON workfit.worker.WORKER_ID = workfit.spent_holiday.WORKER_ID and workfit.days_of_work.MONTH_ID = workfit.spent_holiday.MONTH_ID
WHERE  workfit.days_of_work.MONTH_ID = 1 and workfit.days_of_work.WORKER_ID = 3 ;