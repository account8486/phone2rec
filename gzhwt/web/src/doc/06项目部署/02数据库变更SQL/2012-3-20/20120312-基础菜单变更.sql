DELETE FROM data_dict_config
WHERE data_type='terminal_type'
AND data_code='CLIENT';


DELETE FROM base_menu 
WHERE terminal_type='CLIENT';

DELETE FROM meeting_client_menu 
WHERE terminal_type='CLIENT';

DELETE FROM base_menu 
WHERE meeting_type IS NOT NULL
AND meeting_type>1;


