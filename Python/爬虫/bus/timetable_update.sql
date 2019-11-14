UPDATE timetable set station = TRIM(substring(line_name, 1, INSTR(line_name, "-")-1)), arrive = TRIM(substring(line_name, INSTR(line_name, "-")+1, LENGTH(line_name)))
