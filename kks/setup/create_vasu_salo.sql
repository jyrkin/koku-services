insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	60
  ,'kks.kehitysasialaji.vasu.salo'   -- type_code
  ,'Varhaiskasvatussuunnitelma'  -- name
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
   550   -- group_id
  ,1   -- sort_order
  ,'Huoltaja'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,60
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
   551   -- group_id
  ,2   -- sort_order
  ,'P�iv�hoito'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,60
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
   552   -- group_id
  ,1   -- sort_order
  ,'Elinolosuhteet'  -- name
  ,'Asiat, jotka vaikuttavat p�ivitt�iseen kasvatuskumppanuuteen'  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,550   -- parent_id
  ,60
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
   553   -- group_id
  ,2   -- sort_order
  ,'Lapsen hyvinvointi'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,550   -- parent_id
  ,60
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
   554   -- group_id
  ,3   -- sort_order
  ,'Mediakasvatus'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'guardian' -- accountable
  ,550   -- parent_id
  ,60
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
   555   -- group_id
  ,1   -- sort_order
  ,'Tavoitteet'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,551   -- parent_id
  ,60
);

-- Huoltaja

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
   600   -- entry_class_id
  ,1   -- sort_order
  ,'Lapsen �idinkieli?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,552   -- entry_group
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
   601   -- entry_class_id
  ,2   -- sort_order
  ,'Muut perheess� puhutut kielet?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,552   -- entry_group
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
   602   -- entry_class_id
  ,3   -- sort_order
  ,'Tapaamiset, jos vanhemmat asuvat erill��n?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,552   -- entry_group
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
   603   -- entry_class_id
  ,4   -- sort_order
  ,'Mik�li vanhemmilla on yhteishuoltajuus, voiko huoltajan avo/aviopuoliso osallistua varhaiskasvatuskeskusteluun?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECTION'  -- data_type
  ,'kyll�,ei'  -- value_spaces
  ,552   -- entry_group
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
   604   -- entry_class_id
  ,5   -- sort_order
  ,'Muut mahdolliset asiat?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,552   -- entry_group
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
   605   -- entry_class_id
  ,6   -- sort_order
  ,'Nukkuminen ja p�iv�lepo?'  -- name
  ,'Miten pitk�t y�unet, her��minen, vireystila her��misen j�lkeen jne.'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   606   -- entry_class_id
  ,7   -- sort_order
  ,'Ruokailutottumukset?'  -- name
  ,'K�ytt��k� ruokailuv�lineit� itsen�isesti, sy�k� siististi, ruokailutavat, mieliruoat ym.'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   607   -- entry_class_id
  ,8   -- sort_order
  ,'WC-k�yt�nn�t ja hygienia?'  -- name
  ,'Vaippa, potta, wc-istuin, omatoimisuus, k�sien peseminen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   608   -- entry_class_id
  ,9   -- sort_order
  ,'Omatoimisuus?'  -- name
  ,'Pukeutuminen, tavaroista huolehtiminen jne.'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   609   -- entry_class_id
  ,10   -- sort_order
  ,'Muuta terveyteen liittyv��?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   610   -- entry_class_id
  ,11   -- sort_order
  ,'Toiveet tuomis- ja hakutilanteissa?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   611   -- entry_class_id
  ,12   -- sort_order
  ,'Huolenaiheiden esille ottaminen?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   612   -- entry_class_id
  ,13   -- sort_order
  ,'Toiveet ja odotukset p�iv�hoidolle?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,553   -- entry_group
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
   613   -- entry_class_id
  ,14   -- sort_order
  ,'Onko teill� s��nt�j� tv:n katselusta, videoista tai tietokonepeleist�?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,554   -- entry_group
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
   614   -- entry_class_id
  ,15   -- sort_order
  ,'Katsotteko yhdess� lapsenne kanssa tv:t�?'  -- name
  ,'Kuinka usein, mit� ohjelmia...'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,554   -- entry_group
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
   615   -- entry_class_id
  ,16   -- sort_order
  ,'Mist� ohjelmista lapsesi pit��?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,554   -- entry_group
);


-- P�iv�hoito

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
   616   -- entry_class_id
  ,17   -- sort_order
  ,'Tuemme lapsen kehityst� seuraavalla tavalla:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,555   -- entry_group
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
   617   -- entry_class_id
  ,18   -- sort_order
  ,'Arviointi:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,555   -- entry_group
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   600   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   600   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   600   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   601   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   601   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   601   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   602   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   602   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   603   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   603   -- entry_class_id
  ,64   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   604   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   604   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   605   -- entry_class_id
  ,18   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   605   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   606   -- entry_class_id
  ,18   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   606   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   607   -- entry_class_id
  ,18   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   607   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   608   -- entry_class_id
  ,18   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   608   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   609   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   609   -- entry_class_id
  ,41   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   610   -- entry_class_id
  ,19   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   610   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   611   -- entry_class_id
  ,19   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   611   -- entry_class_id
  ,40   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   612   -- entry_class_id
  ,19   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   612   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   613   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   613   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   613   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   614   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   614   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   614   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   615   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   615   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   615   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   616   -- entry_class_id
  ,15   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   616   -- entry_class_id
  ,55   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   617   -- entry_class_id
  ,11   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   617   -- entry_class_id
  ,55   -- tag_id
);


