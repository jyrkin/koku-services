insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	5
  ,'kks.kehitysasialaji.5lk_koulu_terveystarkastus.oppilas'   -- type_code
  ,'5. Luokan kouluterveystarkastus, Oppilaan lomake'  -- name
  ,''  -- description
  ,''  -- consent_type
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
   60   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,5
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
   61   -- group_id
  ,1   -- sort_order
  ,'Koulunk�ynti/Kaverit'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,60   -- parent_id
  ,5
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
   62   -- group_id
  ,2   -- sort_order
  ,'El�m�ntavat'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,60   -- parent_id
  ,5
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
   201   -- entry_class_id
  ,1   -- sort_order
  ,'Miten koulunk�yntisi on sujunut?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   202   -- entry_class_id
  ,2   -- sort_order
  ,'Viihdytk� koulussa?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   203   -- entry_class_id
  ,3   -- sort_order
  ,'Oletko tyytyv�inen koulumenestykseesi?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   204   -- entry_class_id
  ,4   -- sort_order
  ,'Pid�tk� opettajastasi?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   205   -- entry_class_id
  ,5   -- sort_order
  ,'Kuinka paljon k�yt�t aikaa l�ksyjen tekoon? Saatko tarvittaessa apua l�ksyjen tekoon?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   224   -- entry_class_id
  ,5   -- sort_order
  ,'Onko sinulla kavereita kotona ja koulussa?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   206   -- entry_class_id
  ,6   -- sort_order
  ,'Kiusataanko, syrjit��nk� tai �rsytet��nk� sinua koulussa tai kaveripiiriss�? Miten?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   207   -- entry_class_id
  ,7   -- sort_order
  ,'Miten selvi�t kiusaamistilanteesta? Miten kiusaamistilanteet on hoidettu? Miten kiusaamistilanteet pit�isi mielest�si hoitaa?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   208   -- entry_class_id
  ,8   -- sort_order
  ,'Kiusaatko itse? Miksi?'  -- name
  ,'Oppilaan koulunk�ynti ja kaverit'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,61   -- entry_group
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
   209   -- entry_class_id
  ,9   -- sort_order
  ,'Paljonko ulkoilet p�ivitt�in (tunteina)?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   210   -- entry_class_id
  ,10   -- sort_order
  ,'Paljonko katsot TV:t� (tunteina)?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   211   -- entry_class_id
  ,11   -- sort_order
  ,'Paljonko pelaat erilaisia pelej� (tunteina)?'  -- name
  ,'Oppilaan vapaa-aika ja liikunta'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   212   -- entry_class_id
  ,12   -- sort_order
  ,'Mit� yleens� teet vapaa-aikanasi? Onko sinulla jokin harrastus?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   213   -- entry_class_id
  ,13   -- sort_order
  ,'Miten hoidat henkil�kohtaisen hygieniasi?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   214   -- entry_class_id
  ,14   -- sort_order
  ,'Kuinka usein harjaat hampaasi?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   215   -- entry_class_id
  ,15   -- sort_order
  ,'Oletko havainnut itsess�si murrosi�n merkkej� kuten iho-ongelmia, hikoilun lis��ntymist� jne?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   216   -- entry_class_id
  ,16   -- sort_order
  ,'KYSYMYS TYT�ILLE: Onko kuukautisesi alkaneet? Milloin?'  -- name
  ,'Oppilaan hygienia'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   217   -- entry_class_id
  ,17   -- sort_order
  ,'Mit� sy�t aamuisin ennen kouluun tuloa?'  -- name
  ,'Oppilaan ruokailu'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   218   -- entry_class_id
  ,18   -- sort_order
  ,'Sy�tk� kouluruoan?'  -- name
  ,'Oppilaan ruokailu'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   219   -- entry_class_id
  ,19   -- sort_order
  ,'Miten nukut? H�iritseek� joku asia untasi?'  -- name
  ,'Oppilaan el�m�ntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   220   -- entry_class_id
  ,20   -- sort_order
  ,'Miten suhtaudut tupakkaan ja p�ihteisiin?'  -- name
  ,'Oppilaan el�m�ntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   221   -- entry_class_id
  ,21   -- sort_order
  ,'Oletko mielest�si terve? Miksi et?'  -- name
  ,'Oppilaan el�m�ntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   222   -- entry_class_id
  ,22   -- sort_order
  ,'Oletko yleens� iloinen ja tyytyv�inen itseesi? Miksi et?'  -- name
  ,'Oppilaan el�m�ntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
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
   223   -- entry_class_id
  ,23   -- sort_order
  ,'Huolestuttaako sinua jokin asia t�ll� hetkell� itsess�si, kaveripiiriss�, kotona tai koulussa? Mik�?'  -- name
  ,'Oppilaan el�m�ntavat'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,62   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   201   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   20   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   202   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   202   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   203   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   203   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   204   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   204   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   205   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   205   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   206   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   206   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   207   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   207   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   224   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   224   -- entry_class_id
  ,66   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   208   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   208   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   209   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   209   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   210   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   210   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   211   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   211   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   212   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   212   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   213   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   213   -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   214   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   214  -- entry_class_id
  ,67   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   215   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   215   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   216   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   216   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   217   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   217   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   218   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   218   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   219   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   219   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   220   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   220   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   221   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   221   -- entry_class_id
  ,41   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   222   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   222   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   223   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   223   -- entry_class_id
  ,40   -- tag_id
);

