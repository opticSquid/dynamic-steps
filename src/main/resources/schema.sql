create database if not exists esis;
create database if not exists na;
create database if not exists local;
-- esis db
create table esis_claims(
BATCH_ID INTEGER PRIMARY KEY,
BATCH_STATUS INTEGER,
VENDOR_NAME VARCHAR(50),
FILE_TYPE VARCHAR(50),
RECORD_COUNT INTEGER,
SUCCESS_COUNT INTEGER,
FAILED_COUNT INTEGER,
DUPLICATE_COUNT INTEGER,
RECEIVED_DATE TIMESTAMP,
FILENET_GUID VARCHAR(50)
);
-- na db
create table claim_connect(
BATCH_ID INTEGER PRIMARY KEY,
BATCH_STATUS INTEGER,
VENDOR_NAME VARCHAR(50),
FILE_TYPE VARCHAR(50),
RECORD_COUNT INTEGER,
SUCCESS_COUNT INTEGER,
FAILED_COUNT INTEGER,
DUPLICATE_COUNT INTEGER,
RECEIVED_DATE TIMESTAMP,
FILENET_GUID VARCHAR(50)
);

create table gf_ace_submission_quote(
GF_ACE_SUBMISSION_QUOTE_ID INTEGER PRIMARY KEY,
APP_DB_STATUS_CODE VARCHAR(1),
APP_DB_RETRY_COUNT INTEGER,
FN_STATUS_CODE VARCHAR(1),
FN_RETRY_COUNT INTEGER,
DATE_ADDED TIMESTAMP
);

create table gf_ace_rt_policy(
GF_ACE_POLICY_ID INTEGER PRIMARY KEY,
APP_DB_STATUS_CODE VARCHAR(1),
APP_DB_RETRY_COUNT INTEGER,
FN_STATUS_CODE VARCHAR(1),
FN_RETRY_COUNT INTEGER,
DATE_ADDED TIMESTAMP
);

create table bwh_claims_account(
GF_ACCOUNT_ID INTEGER PRIMARY KEY,
APP_DB_STATUS_CODE VARCHAR(1),
APP_DB_RETRY_COUNT INTEGER,
FN_STATUS_CODE VARCHAR(1),
FN_RETRY_COUNT INTEGER,
DATE_ADDED TIMESTAMP
);

-- local
create table raw_data(
ID SERIAL PRIMARY KEY,
SOURCE_SYSTEM VARCHAR(50),
DATE_RECEIVED TIMESTAMP,
APPDB_STATUS_CODE VARCHAR(1),
FILENET_STATUS_CODE VARCHAR(1),
APPDB_RETRY_COUNT INTEGER,
FILENET_RETRY_COUNT INTEGER,
BATCH_STATUS INTEGER,
RECORD_COUNT INTEGER,
SUCCESS_COUNT INTEGER,
FAILURE_COUNT INTEGER,
DUPLICATE_COUNT INTEGER,
FILENET_GUID VARCHAR(50)
);