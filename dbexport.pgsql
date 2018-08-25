--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4 (Debian 10.4-2.pgdg90+1)
-- Dumped by pg_dump version 10.4 (Debian 10.4-2.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: answer; Type: TABLE; Schema: public; Owner: chat
--

CREATE TABLE public.answer (
    answer_id bigint NOT NULL,
    answer character varying(255),
    answer_status integer
);


ALTER TABLE public.answer OWNER TO chat;

--
-- Name: answer_answer_id_seq; Type: SEQUENCE; Schema: public; Owner: chat
--

CREATE SEQUENCE public.answer_answer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.answer_answer_id_seq OWNER TO chat;

--
-- Name: answer_answer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chat
--

ALTER SEQUENCE public.answer_answer_id_seq OWNED BY public.answer.answer_id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: chat
--

CREATE TABLE public.person (
    person_id bigint NOT NULL,
    person_email character varying(255),
    person_name character varying(255),
    person_phone character varying(255)
);


ALTER TABLE public.person OWNER TO chat;

--
-- Name: person_person_id_seq; Type: SEQUENCE; Schema: public; Owner: chat
--

CREATE SEQUENCE public.person_person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_person_id_seq OWNER TO chat;

--
-- Name: person_person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chat
--

ALTER SEQUENCE public.person_person_id_seq OWNED BY public.person.person_id;


--
-- Name: question; Type: TABLE; Schema: public; Owner: chat
--

CREATE TABLE public.question (
    question_id bigint NOT NULL,
    question character varying(255),
    question_status integer,
    answer_id bigint,
    response_id bigint
);


ALTER TABLE public.question OWNER TO chat;

--
-- Name: question_question_id_seq; Type: SEQUENCE; Schema: public; Owner: chat
--

CREATE SEQUENCE public.question_question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.question_question_id_seq OWNER TO chat;

--
-- Name: question_question_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chat
--

ALTER SEQUENCE public.question_question_id_seq OWNED BY public.question.question_id;


--
-- Name: satisfy; Type: TABLE; Schema: public; Owner: chat
--

CREATE TABLE public.satisfy (
    satisfy_id bigint NOT NULL,
    satisfy integer,
    satisfy_date bigint,
    person_id bigint NOT NULL,
    question_id bigint NOT NULL
);


ALTER TABLE public.satisfy OWNER TO chat;

--
-- Name: satisfy_satisfy_id_seq; Type: SEQUENCE; Schema: public; Owner: chat
--

CREATE SEQUENCE public.satisfy_satisfy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.satisfy_satisfy_id_seq OWNER TO chat;

--
-- Name: satisfy_satisfy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chat
--

ALTER SEQUENCE public.satisfy_satisfy_id_seq OWNED BY public.satisfy.satisfy_id;


--
-- Name: answer answer_id; Type: DEFAULT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.answer ALTER COLUMN answer_id SET DEFAULT nextval('public.answer_answer_id_seq'::regclass);


--
-- Name: person person_id; Type: DEFAULT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.person ALTER COLUMN person_id SET DEFAULT nextval('public.person_person_id_seq'::regclass);


--
-- Name: question question_id; Type: DEFAULT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.question ALTER COLUMN question_id SET DEFAULT nextval('public.question_question_id_seq'::regclass);


--
-- Name: satisfy satisfy_id; Type: DEFAULT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.satisfy ALTER COLUMN satisfy_id SET DEFAULT nextval('public.satisfy_satisfy_id_seq'::regclass);


--
-- Data for Name: answer; Type: TABLE DATA; Schema: public; Owner: chat
--

COPY public.answer (answer_id, answer, answer_status) FROM stdin;
1	bonjour 	1
2	Boujour comment allez vous 	1
3	La bourse est de $100 par mois	1
4	Bourse de Master 1 et Master 2	1
5	les masters de SIM et Reseau	1
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: chat
--

COPY public.person (person_id, person_email, person_name, person_phone) FROM stdin;
1	sss.sonfack@gmail.com	vu 	
2	sss_sonfack@yahoo.com	vu 	
3	sss.sonfack@gmail.com	vu 	
\.


--
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: chat
--

COPY public.question (question_id, question, question_status, answer_id, response_id) FROM stdin;
1	bonjour 	1	1	\N
2	salut	1	1	\N
3	hello 	1	1	\N
4	bonjour	1	2	\N
5	quelle bourse offre vous	1	4	\N
6	bourse de master	1	5	\N
\.


--
-- Data for Name: satisfy; Type: TABLE DATA; Schema: public; Owner: chat
--

COPY public.satisfy (satisfy_id, satisfy, satisfy_date, person_id, question_id) FROM stdin;
1	1	1532573682930	2	4
2	1	1532596325543	3	6
3	1	1532596399469	3	5
\.


--
-- Name: answer_answer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chat
--

SELECT pg_catalog.setval('public.answer_answer_id_seq', 5, true);


--
-- Name: person_person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chat
--

SELECT pg_catalog.setval('public.person_person_id_seq', 3, true);


--
-- Name: question_question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chat
--

SELECT pg_catalog.setval('public.question_question_id_seq', 6, true);


--
-- Name: satisfy_satisfy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chat
--

SELECT pg_catalog.setval('public.satisfy_satisfy_id_seq', 3, true);


--
-- Name: answer answer_pkey; Type: CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (answer_id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- Name: question question_pkey; Type: CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT question_pkey PRIMARY KEY (question_id);


--
-- Name: satisfy satisfy_pkey; Type: CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.satisfy
    ADD CONSTRAINT satisfy_pkey PRIMARY KEY (satisfy_id);


--
-- Name: satisfy fk191rp6yk2p9h6p4hwxuxdb3kf; Type: FK CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.satisfy
    ADD CONSTRAINT fk191rp6yk2p9h6p4hwxuxdb3kf FOREIGN KEY (question_id) REFERENCES public.question(question_id);


--
-- Name: question fk2w9qd6mx9oh2vchntaokhlj4f; Type: FK CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT fk2w9qd6mx9oh2vchntaokhlj4f FOREIGN KEY (answer_id) REFERENCES public.answer(answer_id);


--
-- Name: satisfy fkign0af2wu1qy8dol7tn0m7t6k; Type: FK CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.satisfy
    ADD CONSTRAINT fkign0af2wu1qy8dol7tn0m7t6k FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- Name: question fkm3toqlom696gnmlnbcv3bgngy; Type: FK CONSTRAINT; Schema: public; Owner: chat
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT fkm3toqlom696gnmlnbcv3bgngy FOREIGN KEY (response_id) REFERENCES public.answer(answer_id);


--
-- PostgreSQL database dump complete
--

