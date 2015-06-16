-- Populate the analysis_job table
-- Version 1.0
INSERT INTO analysis_job (job_operator,input_file_name,result_directory,is_production_run,sample_id,external_run_ids,pipeline_id,analysis_status_id,re_run_count,complete_time,experiment_type)
  SELECT  'unknown' as job_operator,
          log_file_info.file_id as input_file_name,
          UPPER(TRANSLATE(log_file_info.file_id, '.', '_')) as result_directory,
          1 as is_production_run,
          log_file_info.sample_id as sample_id,
          log_file_info.sra_run_ids as external_run_ids,
          1 as pipeline_id,
          3 as analysis_status_id,
          0 as re_run_count,
          log_file_info.analysis_start_date as complete_time,
          log_file_info.data_type_gta as experiment_type
  FROM log_file_info where log_file_info.pipeline_version_number is null OR log_file_info.pipeline_version_number!='V2.0';
-- Version 2.0
INSERT INTO analysis_job (job_operator,input_file_name,result_directory,is_production_run,sample_id,external_run_ids,pipeline_id,analysis_status_id,re_run_count,complete_time,experiment_type)
  SELECT  'unknown' as job_operator,
          log_file_info.file_id as input_file_name,
          CONCAT('version_2.0/',UPPER(TRANSLATE(log_file_info.file_id, '.', '_'))) as result_directory,
          1 as is_production_run,
          log_file_info.sample_id as sample_id,
          log_file_info.sra_run_ids as external_run_ids,
          2 as pipeline_id,
          3 as analysis_status_id,
          0 as re_run_count,
          log_file_info.analysis_start_date as complete_time,
          log_file_info.data_type_gta as experiment_type
  FROM log_file_info where log_file_info.pipeline_version_number='V2.0';