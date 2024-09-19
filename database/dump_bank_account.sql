--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: unaccent(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.unaccent(character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$ DECLARE p_str    alias for $1; v_str    varchar; BEGIN select translate(p_str, 'ÀÁÂÃÄÅ', 'AAAAAA') into v_str; select translate(v_str, 'ÉÈËÊ', 'EEEE') into v_str; select translate(v_str, 'ÌÍÎÏ', 'IIII') into v_str; select translate(v_str, 'ÌÍÎÏ', 'IIII') into v_str; select translate(v_str, 'ÒÓÔÕÖ', 'OOOOO') into v_str; select translate(v_str, 'ÙÚÛÜ', 'UUUU') into v_str; select translate(v_str, 'àáâãäå', 'aaaaaa') into v_str; select translate(v_str, 'èéêë', 'eeee') into v_str; select translate(v_str, 'ìíîï', 'iiii') into v_str; select translate(v_str, 'òóôõö', 'ooooo') into v_str; select translate(v_str, 'ùúûü', 'uuuu') into v_str; select translate(v_str, 'Çç', 'Cc') into v_str; return v_str; END;$_$;


ALTER FUNCTION public.unaccent(character varying) OWNER TO postgres;

--
-- Name: update_transactions_recherche_trigger(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_transactions_recherche_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ BEGIN NEW.transactions_recherche = coalesce(lower(unaccent(trim(NEW.transactions_message))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.transactions_amount::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.creation_date::text))),'') ||'|'|| coalesce((SELECT lower(unaccent(trim(user_name))) FROM users WHERE user_id = NEW.transactions_user_id),'') ||'|'|| coalesce((SELECT lower(unaccent(trim(user_first_name))) FROM users WHERE user_id = NEW.transactions_user_id),'') ||'|'|| coalesce((SELECT lower(unaccent(trim(user_email))) FROM users WHERE user_id = NEW.transactions_user_id),'') ||'|'|| coalesce((SELECT lower(unaccent(trim(user_account_balance::text))) FROM users WHERE user_id = NEW.transactions_user_id),''); RETURN NEW; END;$$;


ALTER FUNCTION public.update_transactions_recherche_trigger() OWNER TO postgres;

--
-- Name: update_user_recherche_trigger(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_user_recherche_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ BEGIN NEW.user_recherche = coalesce(lower(unaccent(trim(NEW.user_account_balance::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_address::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_date_of_birth::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_email::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_first_name::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_job_title::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_name::text))),'') ||'|'|| coalesce(lower(unaccent(trim(NEW.user_phone_number::text))),''); RETURN NEW; END;$$;


ALTER FUNCTION public.update_user_recherche_trigger() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    transactions_id bigint NOT NULL,
    creation_date timestamp(6) without time zone,
    modification_date timestamp(6) without time zone,
    transactions_amount double precision,
    transactions_message character varying(255),
    transactions_recherche character varying(255),
    transactions_user_id bigint
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- Name: transactions_transactions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.transactions ALTER COLUMN transactions_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.transactions_transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    creation_date timestamp(6) without time zone,
    modification_date timestamp(6) without time zone,
    user_account_balance double precision,
    user_address character varying(255),
    user_date_of_birth timestamp(6) without time zone,
    user_email character varying(255) NOT NULL,
    user_first_name character varying(255) NOT NULL,
    user_job_title character varying(255) NOT NULL,
    user_name character varying(255) NOT NULL,
    user_phone_number character varying(255),
    user_recherche character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
01_create_search_multicritere_transaction	lhuyghe	db/changelog/01_create_search_multicritere_transaction.yaml	2024-07-18 14:59:52.514143	1	EXECUTED	9:3354b8cb71d74af02e19e6bbdad0441d	modifyDataType columnName=transactions_recherche, tableName=transactions; sql; sql; sql; sql	Ajout d'un champ recherche	\N	4.27.0	nominal	\N	1307592465
02_create_search_multicritere_user	lhuyghe	db/changelog/02_create_search_multicritere_user.yaml	2024-07-18 14:59:52.534451	2	EXECUTED	9:85472c1211242b06fa321d74804bdbba	modifyDataType columnName=user_recherche, tableName=users; sql; sql; sql; sql	Ajout d'un champ recherche	\N	4.27.0	nominal	\N	1307592465
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transactions (transactions_id, creation_date, modification_date, transactions_amount, transactions_message, transactions_recherche, transactions_user_id) FROM stdin;
1	2024-07-18 15:08:51.077	2024-07-18 15:08:51.077	150.75	Payment for services	payment for services|150.75|2024-07-18 15:08:51.077|doe|john|john.doe@example.com|	1000
2	2024-07-18 15:09:28.323	2024-07-18 15:09:28.323	50	Refund for order #12345	refund for order #12345|50|2024-07-18 15:09:28.323|martin|paul|paul.martin@example.com|	1002
3	2024-07-18 15:09:51.803	2024-07-18 15:09:51.803	-350.5	Invoice #56789	invoice #56789|-350.5|2024-07-18 15:09:51.803|lefevre|marie|marie.lefevre@example.com|	1004
4	2024-07-18 15:10:07.984	2024-07-18 15:10:07.984	-450	Consulting fee	consulting fee|-450|2024-07-18 15:10:07.984|doe|john|john.doe@example.com|150.75	1000
5	2024-07-18 15:10:26.904	2024-07-18 15:10:26.904	100	saved money	saved money|100|2024-07-18 15:10:26.904|smith|jane|jane.smith@example.com|	1001
6	2024-07-18 15:10:45.046	2024-07-18 15:10:45.046	-400	vacations	vacations|-400|2024-07-18 15:10:45.046|smith|jane|jane.smith@example.com|100	1001
7	2024-07-18 15:11:10.429	2024-07-18 15:11:10.429	-1000	rental	rental|-1000|2024-07-18 15:11:10.429|smith|jane|jane.smith@example.com|-300	1001
8	2024-07-18 15:11:25.669	2024-07-18 15:11:25.669	2000	Salaries	salaries|2000|2024-07-18 15:11:25.669|smith|jane|jane.smith@example.com|-1300	1001
9	2024-07-18 15:11:39.945	2024-07-18 15:11:39.945	10	init account	init account|10|2024-07-18 15:11:39.945|dupont|alice|alice.dupont@example.com|	1003
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, creation_date, modification_date, user_account_balance, user_address, user_date_of_birth, user_email, user_first_name, user_job_title, user_name, user_phone_number, user_recherche) FROM stdin;
1002	2024-07-01 00:00:00	2024-07-18 15:09:28.331	50	789 Saint Michael Blvd, City, Country	1978-05-23 00:00:00	paul.martin@example.com	Paul	Project Manager	Martin	0033613456789	50|789 saint michael blvd, city, country|1978-05-23 00:00:00|paul.martin@example.com|paul|project manager|martin|0033613456789
1004	2024-07-01 00:00:00	2024-07-18 15:09:51.808	-350.5	654 Rivoli St, City, Country	1987-11-30 00:00:00	marie.lefevre@example.com	Marie	Developer	Lefevre	0033615678901	-350.5|654 rivoli st, city, country|1987-11-30 00:00:00|marie.lefevre@example.com|marie|developer|lefevre|0033615678901
1000	2024-07-01 00:00:00	2024-07-18 15:10:07.992	-299.25	123 Main St, City, Country	1990-01-01 00:00:00	john.doe@example.com	John	Software Engineer	Doe	0033612345678	-299.25|123 main st, city, country|1990-01-01 00:00:00|john.doe@example.com|john|software engineer|doe|0033612345678
1001	2024-07-01 00:00:00	2024-07-18 15:11:25.675	700	456 Elm St, City, Country	1985-02-15 00:00:00	jane.smith@example.com	Jane	Data Analyst	Smith	0033723456789	700|456 elm st, city, country|1985-02-15 00:00:00|jane.smith@example.com|jane|data analyst|smith|0033723456789
1003	2024-07-01 00:00:00	2024-07-18 15:11:39.956	10	321 Champs-Elysees Ave, City, Country	1992-08-14 00:00:00	alice.dupont@example.com	Alice	Consultant	Dupont	0033724567890	10|321 champs-elysees ave, city, country|1992-08-14 00:00:00|alice.dupont@example.com|alice|consultant|dupont|0033724567890
\.


--
-- Name: transactions_transactions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transactions_transactions_id_seq', 9, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 1, false);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (transactions_id);


--
-- Name: users uk33uo7vet9c79ydfuwg1w848f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk33uo7vet9c79ydfuwg1w848f UNIQUE (user_email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: transactions update_transactions_recherche_trigger_before_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_transactions_recherche_trigger_before_insert BEFORE INSERT ON public.transactions FOR EACH ROW EXECUTE FUNCTION public.update_transactions_recherche_trigger();


--
-- Name: transactions update_transactions_recherche_trigger_before_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_transactions_recherche_trigger_before_update BEFORE UPDATE ON public.transactions FOR EACH ROW EXECUTE FUNCTION public.update_transactions_recherche_trigger();


--
-- Name: users update_users_recherche_trigger_before_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_users_recherche_trigger_before_insert BEFORE INSERT ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_user_recherche_trigger();


--
-- Name: users update_users_recherche_trigger_before_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_users_recherche_trigger_before_update BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.update_user_recherche_trigger();


--
-- Name: transactions fkom9oj7x36duy3e9q11eun08es; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fkom9oj7x36duy3e9q11eun08es FOREIGN KEY (transactions_user_id) REFERENCES public.users(user_id);


--
-- PostgreSQL database dump complete
--

