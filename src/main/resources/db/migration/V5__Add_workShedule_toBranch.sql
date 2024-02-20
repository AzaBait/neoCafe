alter table if exists branches
       add constraint fk_branch_workSchedule
       foreign key (work_schedule_id)
       references work_schedules(id);