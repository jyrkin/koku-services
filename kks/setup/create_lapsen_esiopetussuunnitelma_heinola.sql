insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	130
  ,'kks.kehitysasialaji.lapsen_esiopetussuunnitelma.heinola'   -- type_code
  ,'Lapsen esiopetussuunnitelma'  -- name
  ,'Esiopetus on leikin ja toiminnan avulla tapahtuvaa tavoitteellista toimintaa.<br/> 
	Esiopetussuunnitelman tekeminen on osa perheen ja esiopetuksen v�list� kasvatuskumppanuutta. Kasvatuskumppanuus l�htee lapsen tarpeista ja siin� yhdistyv�t lapselle kahden t�rke�n tahon, vanhempien ja varhaiskasvatuksen kasvattajien tiedot ja kokemukset.<br/>
Esiopetussuunnitelma tehd��n yhteisty�ss� perheen ja esiopetuksesta vastaavan aikuisen kanssa. T�m�n avulla sovitaan yhteisty�ss� perheen kanssa lapsen kasvatustavoitteista ja seurataan lapsen kehityst� esiopetusvuoden aikana. L�ht�kohtana on, ett� suunnitelmat ovat aktiivisessa k�yt�ss� ja niiden sis�lt� on sek� vanhempien ett� koko esiopetushenkil�st�n tiedossa ja arvioitavissa.<br/>
Teemme esiopetuksessa moniammatillista yhteisty�t� taataksemme laadukkaan esiopetuksen. Yhteisty�kumppaneita ovat mm. neuvola, koulu, psykologit ja terapeutit. Ensisijainen yhteisty�kumppani on kuitenkin aina lapsen oma perhe. Esiin tulevat asiat ovat luottamuksellisia ja tietoja voidaan antaa ulkopuolisille vain vanhempien luvalla ja heid�n kanssa keskustellen.<br/>Esiopetussuunnitelman koontisivu l�hetet��n vanhempien suostumuksella lapsen tulevaan kouluun.<br/>
'  -- description
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
   900   -- group_id
  ,1   -- sort_order
  ,'Huoltaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,130
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
   901   -- group_id
  ,1   -- sort_order
  ,'Esiopetus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,130
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
   902   -- group_id
  ,1   -- sort_order
  ,'P�ivitt�istoiminnot'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Ruokailu</li>
	<li>Omista tavaroista huolehtiminen</li> 
	<li>Pukeutuminen ja riisuminen</li>
	<li>Wc-k�ytt�ytyminen, omasta hygieniasta huolehtiminen</li> 
	<li>P�iv�lepo</li>
	<li>P�iv�rytmin omaksuminen</li>
	<li>Miten toiminnot sujuvat, mit� huomioitavaa, omatoimisuus toiminnoissa</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   903   -- group_id
  ,2   -- sort_order
  ,'Sosiaaliset taidot ja tunneilmaisu'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Suhtautuu aikuisiin luottavaisesti</li>
	<li>Tunteiden nime�minen ja niiden ilmaisun kontrolloiminen sek� pettymysten siet�minen</li>
	<li>Ratkaisee erimielisyyksi� my�nteisin keinoin</li>
	<li>Vuoron odottaminen, s��nt�jen noudattaminen</li>
	<li>Vuorottelee v�lineiden k�yt�ss�</li>
	<li>Itsens� arvostaminen</li>
	<li>Hyv�t tavat</li>
	<li>Yhdess� leikkimisen taidot; valmius neuvotella ja tehd� kompromisseja sek� solmia yst�vyyssuhteita</li>
	<li>Leikin sis�lt� ja kesto/pitk�j�nteisyys, leikkiv�lineet</li>
	<li>Roolit leikiss� ja mielikuvitus</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   904   -- group_id
  ,3   -- sort_order
  ,'Ty�skentelytaidot'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Osallistuminen, yritt�minen ja suhtautuminen uusiin tilanteisiin ja asioihin</li>
	<li>Lapsen k�ytt�ytyminen oppimistilanteissa (sek� yksil�- ett� ryhm�tilanteissa; ohjeiden vastaanottaminen, toimiminen niiden mukaan ja teht�vien loppuun suorittaminen) sek� vapaassa ryhm�tilanteessa (noudattaa s��nt�j� peleiss� ja leikeiss�)</li>
	<li>Tarkkaavaisuus (yll�pit�minen, siirt�minen, oman toiminnan ohjaaminen)</li>
	<li>Pystyy itsen�iseen ty�skentelyyn</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   905   -- group_id
  ,4   -- sort_order
  ,'Kielelliset valmiudet'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Kielen/puheen tuottaminen; kertoo ja kuvailee, puheen sujuvuus ja selkeys, sana- ja k�sitevarasto ja kielen rakenteet, vertailee asioita</li>
	<li>Kielen/puheen ymm�rt�minen; kuunteleminen ja kuullun ymm�rt�minen sek� muistaminen, ajan ja paikan k�sitteet, p��ttely</li>
	<li>Kiinnostus lukemisen ja kirjoittamisen oppimiseen (esim. tutustuminen kirjaimiin ja alku��nteisiin, rytmi, riimit, oman nimen kirjoittaminen)</li>
	<li>Vuorovaikutus</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   906   -- group_id
  ,5   -- sort_order
  ,'Matemaattiset valmiudet, looginen ajattelu ja p��ttely'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Tutustuminen matemaattisiin k�sitteisiin (esim.yht� suuri, enemm�n, keskimm�inen)</li>
	<li>Luettelee ja tunnistaa luvut 1-10 sek� ymm�rt�� kuva-numerovastaavuuden</li>
	<li>Ajan, paikan ja tilan hahmottaminen (esim.viikonp�iv�t)</li>
	<li>Matemaattisen ajattelun ja ongelmanratkaisukyvyn kehitt�minen</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
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
   907   -- group_id
  ,6   -- sort_order
  ,'Motoriikka ja hahmotus'  -- name
  ,'Esimerkiksi:<br/>
  <ul>
	<li>Lapsen ja perheen suhtautuminen liikuntaan</li>
	<li>Oman kehonhallinta ja kehonosien nime�minen ja tunnistaminen</li>
	<li>Perusliikuntataidot (esim. k�vely, juoksu, hiihto, luistelu, py�r�ily, uinti)</li> 
	<li>Erilaisten ty�skentelyv�lineiden hallinta (esim. liikuntav�lineet, sakset, kyn�, napit, nauhat, helmet, ruokailuv�lineet)</li> 
	<li>Tilan hallinta ja hahmottaminen (esim. liikkuminen tilassa, rakentelu, palapelit, labyrintit)</li>
	<li>Vakiintunut k�tisyys (oikea tai vasen)</li>
  </ul>'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,900   -- parent_id
  ,130
);

-- Esiopetus

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
   908   -- group_id
  ,1   -- sort_order
  ,'P�ivitt�istoiminnot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   909   -- group_id
  ,2   -- sort_order
  ,'Sosiaaliset taidot ja tunneilmaisu'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   910   -- group_id
  ,3   -- sort_order
  ,'Ty�skentelytaidot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   911   -- group_id
  ,4   -- sort_order
  ,'Kielelliset valmiudet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   912   -- group_id
  ,5   -- sort_order
  ,'Matemaattiset valmiudet, looginen ajattelu ja p��ttely'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
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
   913   -- group_id
  ,6   -- sort_order
  ,'Motoriikka ja hahmotus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'employee' -- accountable
  ,901   -- parent_id
  ,130
);

-- Kent�t

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
   1250   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,902   -- entry_group
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
   1251   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,903   -- entry_group
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
   1252   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,904   -- entry_group
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
   1253   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,905  -- entry_group
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
   1254   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,906   -- entry_group
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
   1255   -- entry_class_id
  ,1   -- sort_order
  ,'Mitk� t�m�n osa-alueen asiat ovat mielest�si oleellisia eskarivuonna sinun lapsesi kannalta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,907   -- entry_group
);

-- Esiopetus

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
   1256   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,908   -- entry_group
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
   1257   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,909   -- entry_group
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
   1258   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,910   -- entry_group
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
   1259   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,911   -- entry_group
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
   1260   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,912   -- entry_group
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
   1261   -- entry_class_id
  ,1   -- sort_order
  ,'Eskarin huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,913   -- entry_group
);

-- huoltaja

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1250   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1250   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1251   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1251   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1252   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1252   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1253   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1253   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1254   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1254   -- entry_class_id
  ,31   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1255   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1255   -- entry_class_id
  ,21   -- tag_id
);


-- Esiopetus


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1256   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1256   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1257   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1257   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1258   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1258   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1259   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1259   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1260   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1260   -- entry_class_id
  ,31   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1261   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1261   -- entry_class_id
  ,21   -- tag_id
);
