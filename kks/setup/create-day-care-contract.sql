insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	3
  ,'kks.kehitysasialaji.sopimus_paivakotihoito'   -- type_code
  ,'P�iv�kotihoidon Hoitosopimus'  -- name
  ,''  -- description
  ,''  -- concent_type
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   30   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,3
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   31   -- group_id
  ,2   -- sort_order
  ,'Ilta- ja vuorohoitosopimuksessa lis�ksi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,3
);

insert into kks_group (
  group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,collection_class_id
) VALUES (
   32   -- group_id
  ,3   -- sort_order
  ,'Perhep�iv�hoidon sopimuksessa lis�ksi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   33   -- group_id
  ,1   -- sort_order
  ,'Vanhempi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   34   -- group_id
  ,2   -- sort_order
  ,'Hoitoaika'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   35   -- group_id
  ,3   -- sort_order
  ,'Lapsen hyvinvointi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   36   -- group_id
  ,4   -- sort_order
  ,'P�iv�kodin johtaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   37   -- group_id
  ,5   -- sort_order
  ,'Lapsen sairastuminen'  -- name
  ,'Huoltajat itse j�rjest�v�t sairaan lapsen hoidon. P�iv�hoidon henkil�kunnalla on oikeus kirjoittaa todistus lapsen sairastumisesta ty�nantajalle sairastumisp�iv�n osalta (Kvtes: perhevapaat � 10).'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   38   -- group_id
  ,6   -- sort_order
  ,'Luvat'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   39   -- group_id
  ,7   -- sort_order
  ,'Uskontokasvatus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   40   -- group_id
  ,8   -- sort_order
  ,'P�iv�hoitomatkat'  -- name
  ,'Henkil�t, jotka voivat hakea lapseni hoitopaikasta huoltajien lis�ksi. P��s��nt�isesti aikuinen hakee lapsen p�iv�hoidosta. P�iv�hoitohenkil�kunnan velvollisuus on varmistaa, ett� lasta noutavalla henkil�ll� on oikeus noutaa lapsi. Mik�li joku muu kuin huoltaja tai alla mainittu varahakija hakee lapsen p�iv�hoidosta, huoltajat ilmoittavat siit� p�iv�hoidon henkil�kunnalle kirjallisesti. Erityistapauksissa, joissa huoltajat esitt�v�t lapsen hakijaksi alaik�ist� sisarusta tai muuta vastaavaa henkil�� tai toivovat lapsen kulkevan yksin, p�iv�hoitohenkil�kunta arvioi yhdess� huoltajien kanssa keskustellen lapsen kotimatkan turvallisuuden ja sopii siit� kirjallisesti'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,30   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   41   -- group_id
  ,1   -- sort_order
  ,'Hoitoaika'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,31   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   42   -- group_id
  ,1   -- sort_order
  ,'Hoitaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,32   -- parent_id
  ,3
);

insert into kks_group (
   group_id
  ,sort_order
  ,name
  ,description
  ,register
  ,accountable
  ,parent_id
  ,collection_class_id
) VALUES (
   43   -- group_id
  ,2   -- sort_order
  ,'Varahoitopaikka'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,32   -- parent_id
  ,3
);
  

  
insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   100   -- entry_class_id
  ,1   -- sort_order
  ,'Huoltaja 1) nimi,ty�tilanne/ty�paikka'  -- name
  ,'Lapsen huoltajan ty�tilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   101   -- entry_class_id
  ,2   -- sort_order
  ,'Huoltajan 1) Puh.kotiin/ty�paikalle'  -- name
  ,'Lapsen huoltajan puhelintiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   102   -- entry_class_id
  ,3   -- sort_order
  ,'Huoltaja 1) Haluan k�ytt�� s�hk�postia yhten� viestiv�lineen�'  -- name
  ,'Lasten henkil�kohtaisia asioita ei sovita eik� henkil�tietoja k�sitell� koskaan s�hk�postitse, mutta p�iv�kodin tai lapsiryhm�n yleisi� asioita voidaan tiedottaa s�hk�postitse.'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyll�,En'  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   103   -- entry_class_id
  ,4   -- sort_order
  ,'Huoltaja 2) nimi,ty�tilanne/ty�paikka'  -- name
  ,'Lapsen huoltajan ty�tilanne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   104   -- entry_class_id
  ,5   -- sort_order
  ,'Huoltajan 2) Puh.kotiin/ty�paikalle'  -- name
  ,'Lapsen huoltajan puhelintiedot'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   105   -- entry_class_id
  ,6   -- sort_order
  ,'Huoltaja 2) Haluan k�ytt�� s�hk�postia yhten� viestiv�lineen�'  -- name
  ,'Lasten henkil�kohtaisia asioita ei sovita eik� henkil�tietoja k�sitell� koskaan s�hk�postitse, mutta p�iv�kodin tai lapsiryhm�n yleisi� asioita voidaan tiedottaa s�hk�postitse.'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyll�,En'  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   106   -- entry_class_id
  ,7   -- sort_order
  ,'Huoltajuus'  -- name
  ,'Huoltajuuden tyyppi'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yksinhuoltaja, Yhteishuoltajuus'  -- value_spaces
  ,33   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   107   -- entry_class_id
  ,8   -- sort_order
  ,'Hoitosuhde alkaa'  -- name
  ,'Hoitosuhteen alkamisp�iv�'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   108   -- entry_class_id
  ,9   -- sort_order
  ,'Hoitoaika p�ivitt�in'  -- name
  ,'(esim. klo 8:00-16:00)'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   109   -- entry_class_id
  ,10   -- sort_order
  ,'Perheelle on kerrottu p�iv�hoitopaikkatakuun ja kes�ajan maksuttomuuden periaatteista sek� avoimen varhaiskasvatuksen  palveluista sek� sopimusp�iv�mahdollisuudesta'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Kyll�'  -- value_spaces
  ,34   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   110   -- entry_class_id
  ,11   -- sort_order
  ,'Sopimusp�iv�t per kk'  -- name
  ,'Sopimusp�iv�t varataan etuk�teen, p�ivi� voi vaihtaa ainoastaan ty�st� tai opiskelusta johtuvista syist�. Varatut sopimusp�iv�t ovat voimassa 5 kuukautta.'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,34   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   111   -- entry_class_id
  ,12   -- sort_order
  ,'Sovitut hoitop�iv�t'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'ma,ti,ke,to,pe'  -- value_spaces
  ,34   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   112   -- entry_class_id
  ,13   -- sort_order
  ,'Sairaudet ja allergiat'  -- name
  ,'(pitk�aikaissairaudet, l��kitykset, ruokavaliot)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,35   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   113   -- entry_class_id
  ,14   -- sort_order
  ,'Muut huomioitavat asiat'  -- name
  ,'(esim. lapsen erityisen tuen tarpeeseen liittyv�t asiat, mahdolliset lausunnot, kuntoutuspalvelut tai apuv�lineet)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,35   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   114   -- entry_class_id
  ,15   -- sort_order
  ,'P�iv�kodin johtaja'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,36   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   115   -- entry_class_id
  ,16   -- sort_order
  ,'Johtajan yhteystiedot'  -- name
  ,'(s�hk�postiosoite ja puh)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,36   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   116   -- entry_class_id
  ,17   -- sort_order
  ,'Lapsen sairastuessa otetaan ensi sijaisesti yhteytt�:'  -- name
  ,'Lapsen yhteyshenkil�'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,37   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   117   -- entry_class_id
  ,18   -- sort_order
  ,'1. Annan / annamme  p�iv�hoidon henkil�kunnalle luvan valokuvata / videokuvata lastamme p�iv�kodissa ja retkill� p�iv�hoidon omaan k�ytt��n'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   118   -- entry_class_id
  ,19   -- sort_order
  ,'2. Lapsemme valokuvia ja piirustuksia saa julkaista lapsen Digitaalisessa kasvun kansiossa (t�m� ei ole k�yt�ss� kaikissa p�iv�kodeissa)'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   119   -- entry_class_id
  ,20   -- sort_order
  ,'3. Lapsemme valokuvia ja piirustuksia saa julkaista p�iv�hoidon intranet ja internet-sivuilla. Yksityisyyssuojak�yt�nt�jen mukaan lapsen nime� ja valokuvaa ei intranetissa ja internetiss� yhdistet�, ellei asiasta ole erikseen sovittu.'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   120   -- entry_class_id
  ,21   -- sort_order
  ,'4. Annan / annamme luvan p�iv�hoidon ulkopuolisen tiedotusv�lineen edustajan  valokuvata / videokuvata lastamme tv- ja radio-ohjelmiin'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   121   -- entry_class_id
  ,22   -- sort_order
  ,'5. Annan / annamme luvan p�iv�hoidon ulkopuolisen tiedotusv�lineen edustajan . valokuvata / videokuvata lastamme artikkeleihin (sanomalehdet, aikakausilehdet verkkojulkaisuineen)'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   122   -- entry_class_id
  ,23   -- sort_order
  ,'6. Annan / annamme luvan p�iv�hoidon ulkopuolisen tiedotusv�lineen edustajan haastatella lastamme lehti�, tv- ja radio-ohjelmia varten'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   123   -- entry_class_id
  ,24   -- sort_order
  ,'7. Annan / annamme lapsemme osallistua seuraaviin toimintoihin: TV:n ja kuvatallenteiden (videot, dvd:t)  katsominen'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   124   -- entry_class_id
  ,25   -- sort_order
  ,'8. Annan / annamme lapsemme osallistua p�iv�hoidossa teht�viin retkiin'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'saa,ei saa'  -- value_spaces
  ,38   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   125   -- entry_class_id
  ,26   -- sort_order
  ,'Perheen kanssa on keskusteltu p�iv�hoidon uskontokasvatukseen liittyv�t asiat'  -- name
  ,'Uskonkasvatus'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Kyll�'  -- value_spaces
  ,39   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   126   -- entry_class_id
  ,27   -- sort_order
  ,'Annamme lapsemme osallistua uskontokasvatukseen'  -- name
  ,'Luvat'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Kyll�,Ei'  -- value_spaces
  ,39   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   127   -- entry_class_id
  ,28   -- sort_order
  ,'Uskontokunta'  -- name
  ,'Uskontokunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,39   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   128   -- entry_class_id
  ,29   -- sort_order
  ,'Varahakija 1'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   129   -- entry_class_id
  ,30   -- sort_order
  ,'Varahakija 2'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   130   -- entry_class_id
  ,31   -- sort_order
  ,'Varahakija 3'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   131   -- entry_class_id
  ,32   -- sort_order
  ,'Varahakija 4'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   132   -- entry_class_id
  ,33   -- sort_order
  ,'Varahakija 5'  -- name
  ,'Lapsen varahakija'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,40   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   133   -- entry_class_id
  ,34   -- sort_order
  ,'1. Vanhemman ty�vuorot MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   134   -- entry_class_id
  ,35   -- sort_order
  ,'1. Vanhemman ty�vuorot LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   135   -- entry_class_id
  ,36   -- sort_order
  ,'1. Vanhemman ty�vuorot SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   136   -- entry_class_id
  ,37   -- sort_order
  ,'2. Vanhemman ty�vuorot MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   137   -- entry_class_id
  ,38   -- sort_order
  ,'2. Vanhemman ty�vuorot LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   138   -- entry_class_id
  ,39   -- sort_order
  ,'2. Vanhemman ty�vuorot SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   139   -- entry_class_id
  ,40   -- sort_order
  ,'Lapsen hoitoaika MA-PE'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   140   -- entry_class_id
  ,41   -- sort_order
  ,'Lapsen hoitoaika LA'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   141   -- entry_class_id
  ,42   -- sort_order
  ,'Lapsen hoitoaika SU'  -- name
  ,'(esim. 06:00-14:00)'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   142   -- entry_class_id
  ,43   -- sort_order
  ,'Hoidon tarve'  -- name
  ,'Lapsen hoidon tarve'  -- description
  ,0   -- multi_value
  ,'MULTI_SELECT'  -- data_type
  ,'Arki+ilta,Ilta+lauantai,Ilta+lauantai+sunnuntai,y�'  -- value_spaces
  ,41   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   143   -- entry_class_id
  ,44   -- sort_order
  ,'Perhep�iv�hoitaja'  -- name
  ,'Lapsen perhep�iv�hoitaja'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   144   -- entry_class_id
  ,45   -- sort_order
  ,'Osoite'  -- name
  ,'Lapsen perhep�iv�hoitajan osoite'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   145   -- entry_class_id
  ,46   -- sort_order
  ,'Puh.'  -- name
  ,'Lapsen perhep�iv�hoitajan puhelinnumero'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,42   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   146   -- entry_class_id
  ,47   -- sort_order
  ,'P�iv�koti'  -- name
  ,'Lapsen varahoitopaikka'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   147   -- entry_class_id
  ,48   -- sort_order
  ,'Osoite'  -- name
  ,'Lapsen varahoitopaikan osoite'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
);

insert into kks_entry_class (
   entry_class_id
  ,sort_order
  ,name
  ,description
  ,multi_value
  ,data_type
  ,value_spaces
  ,entry_group
) VALUES (
   148   -- entry_class_id
  ,49   -- sort_order
  ,'Puh.'  -- name
  ,'Lapsen varahoitopaikan puhelinnumero'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,43   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   100   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   100   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   101   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   101   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   102   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   102   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   103   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   103   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   104   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   104   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   105   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   106   -- entry_class_id
  ,61   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   107   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   107   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   108   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   108   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   109   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   109   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   110   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   110   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   111   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   111   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   112   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   112   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   113   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   113   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   114   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   114   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   115   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   115   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   116   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   116   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   117   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   117   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   118   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   118   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   119   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   119   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   120   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   120   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   121   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   121   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   122   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   122   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   123   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   123   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   124   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   124   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   125   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   125   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   126   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   126   -- entry_class_id
  ,64   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   127   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   127   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   128   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   128   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   129   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   129   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   130   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   130   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   131   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   131   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   132   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   132   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   133   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   133   -- entry_class_id
  ,62   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   134   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   134   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   135   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   135   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   136   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   136   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   137   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   137   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   138   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   138   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   139   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   139   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   140   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   140   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   141   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   141   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   142   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   142   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   143   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   144   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   145   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   146   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   147   -- entry_class_id
  ,65   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,16   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,63   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   148   -- entry_class_id
  ,65   -- tag_id
);

