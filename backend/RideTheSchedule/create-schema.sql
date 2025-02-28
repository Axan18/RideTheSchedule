
    drop table if exists myschema.app_user cascade;

    alter table if exists myschema.friendship 
       drop constraint if exists FKhhhv5kmelk6whpk4xwurtjwla;

    alter table if exists myschema.friendship 
       drop constraint if exists FKaep11h3k647rvekl8iok1q4si;

    alter table if exists myschema.recurring_task 
       drop constraint if exists FKlk21800vcvph8hw0paclpk7pb;

    alter table if exists myschema.schedule 
       drop constraint if exists FKijvp7na4mm6x93wh6h4ocq0sn;

    alter table if exists myschema.schedule_task_tags 
       drop constraint if exists FK20tlfn1emosbbbvb734rx599b;

    alter table if exists myschema.schedule_task_tags 
       drop constraint if exists FK6k5spkkp7y2gss34hdtx6fpjf;

    alter table if exists myschema.schedule_task 
       drop constraint if exists FKfuyo9nk6y16ru1px7xn5edasw;

    alter table if exists myschema.schedule_task 
       drop constraint if exists FKnjlqddci8gqo3qym2olwqssim;

    alter table if exists myschema.schedule_task_comment 
       drop constraint if exists FK53yhap9aeumkt9cu4xaf8kke3;

    alter table if exists myschema.schedule_task_comment 
       drop constraint if exists FKqjfud2cjundfjh4egtamrx894;

    alter table if exists myschema.shared_schedule 
       drop constraint if exists FKl5dk1mfmlu7td9xsjleep0ao9;

    alter table if exists myschema.shared_schedule 
       drop constraint if exists FKpxu916irvuxjg4vifb63l6yaw;

    alter table if exists myschema.shared_schedule 
       drop constraint if exists FKb9vcbgsie34na00ua6kququh;

    alter table if exists myschema.todo_task 
       drop constraint if exists FKt3fd1ykv7160eb6oy7nyoo5rp;

    drop table if exists myschema.friendship cascade;

    drop table if exists myschema.recurring_task cascade;

    drop table if exists myschema.schedule cascade;

    drop table if exists myschema.schedule_task_tags cascade;

    drop table if exists myschema.schedule_task cascade;

    drop table if exists myschema.schedule_task_comment cascade;

    drop table if exists myschema.shared_schedule cascade;

    drop table if exists myschema.tag cascade;

    drop table if exists myschema.todo_task cascade;

    create table myschema.app_user (
        is_active boolean not null,
        last_login_date date not null,
        created_at timestamp(6),
        last_modified timestamp(6),
        id uuid not null,
        username varchar(50) not null,
        password varchar(60) not null,
        email varchar(255) not null unique,
        primary key (id)
    );

    create table myschema.friendship (
        status smallint not null check (status between 0 and 1),
        last_modified timestamp(6),
        id1 uuid not null,
        id2 uuid not null,
        primary key (id1, id2)
    );

    create table myschema.recurring_task (
        end_date date not null,
        frequency smallint not null check (frequency between 0 and 3),
        start_date date not null,
        id uuid not null,
        schedule_task_id uuid unique,
        primary key (id)
    );

    create table myschema.schedule (
        created_date date not null,
        date date not null,
        last_modified timestamp(6),
        app_user_id uuid,
        id uuid not null,
        primary key (id)
    );

    create table myschema.schedule_task_tags (
        schedule_task_id uuid not null,
        tag_id uuid not null,
        primary key (schedule_task_id, tag_id)
    );

    create table myschema.schedule_task (
        created_at timestamp(6),
        end_time timestamp(6) not null,
        last_modified timestamp(6),
        start_time timestamp(6) not null,
        id uuid not null,
        recurring_task_id uuid unique,
        schedule_id uuid,
        name varchar(50) not null,
        description varchar(255) not null,
        primary key (id)
    );

    create table myschema.schedule_task_comment (
        created_at timestamp(6),
        last_modified timestamp(6),
        author_id uuid,
        id uuid not null,
        schedule_task_id uuid,
        content varchar(255) not null,
        primary key (id)
    );

    create table myschema.shared_schedule (
        id uuid not null,
        schedule_id uuid,
        schedule_owner_id uuid,
        shared_with_id uuid,
        primary key (id)
    );

    create table myschema.tag (
        id uuid not null,
        name varchar(50) not null,
        color varchar(255) not null,
        primary key (id)
    );

    create table myschema.todo_task (
        completed boolean,
        important boolean,
        id uuid not null,
        schedule_id uuid,
        description varchar(100) not null,
        name varchar(255) not null,
        primary key (id)
    );

    alter table if exists myschema.friendship 
       add constraint FKhhhv5kmelk6whpk4xwurtjwla 
       foreign key (id1) 
       references myschema.app_user;

    alter table if exists myschema.friendship 
       add constraint FKaep11h3k647rvekl8iok1q4si 
       foreign key (id2) 
       references myschema.app_user;

    alter table if exists myschema.recurring_task 
       add constraint FKlk21800vcvph8hw0paclpk7pb 
       foreign key (schedule_task_id) 
       references myschema.schedule_task;

    alter table if exists myschema.schedule 
       add constraint FKijvp7na4mm6x93wh6h4ocq0sn 
       foreign key (app_user_id) 
       references myschema.app_user;

    alter table if exists myschema.schedule_task_tags 
       add constraint FK20tlfn1emosbbbvb734rx599b 
       foreign key (tag_id) 
       references myschema.tag;

    alter table if exists myschema.schedule_task_tags 
       add constraint FK6k5spkkp7y2gss34hdtx6fpjf 
       foreign key (schedule_task_id) 
       references myschema.schedule_task;

    alter table if exists myschema.schedule_task 
       add constraint FKfuyo9nk6y16ru1px7xn5edasw 
       foreign key (recurring_task_id) 
       references myschema.recurring_task;

    alter table if exists myschema.schedule_task 
       add constraint FKnjlqddci8gqo3qym2olwqssim 
       foreign key (schedule_id) 
       references myschema.schedule;

    alter table if exists myschema.schedule_task_comment 
       add constraint FK53yhap9aeumkt9cu4xaf8kke3 
       foreign key (author_id) 
       references myschema.app_user;

    alter table if exists myschema.schedule_task_comment 
       add constraint FKqjfud2cjundfjh4egtamrx894 
       foreign key (schedule_task_id) 
       references myschema.schedule_task;

    alter table if exists myschema.shared_schedule 
       add constraint FKl5dk1mfmlu7td9xsjleep0ao9 
       foreign key (schedule_id) 
       references myschema.schedule;

    alter table if exists myschema.shared_schedule 
       add constraint FKpxu916irvuxjg4vifb63l6yaw 
       foreign key (schedule_owner_id) 
       references myschema.app_user;

    alter table if exists myschema.shared_schedule 
       add constraint FKb9vcbgsie34na00ua6kququh 
       foreign key (shared_with_id) 
       references myschema.app_user;

    alter table if exists myschema.todo_task 
       add constraint FKt3fd1ykv7160eb6oy7nyoo5rp 
       foreign key (schedule_id) 
       references myschema.schedule;
