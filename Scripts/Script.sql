-------------------------------------------------------------------------------
-- select all data

select * from users;
select * from addresses;

-------------------------------------------------------------------------------
-- DELETE
-------------------------------------------------------------------------------
delete from users;
delete from addresses;
-------------------------------------------------------------------------------
-- drop tables
-------------------------------------------------------------------------------

drop table users;
drop table addresses;


select * from users where id = 2339;
select * from addresses where users_id = 2339;


select
        userentity0_.id as id1_1_,
        userentity0_.email as email2_1_,
        userentity0_.email_verification_status as email_ve3_1_,
        userentity0_.email_verification_token as email_ve4_1_,
        userentity0_.encrypted_password as encrypte5_1_,
        userentity0_.first_name as first_na6_1_,
        userentity0_.last_name as last_nam7_1_,
        userentity0_.public_user_id as public_u8_1_ 
    from
        users userentity0_ 
    where
        userentity0_.email='gigi1@muschi.ro';