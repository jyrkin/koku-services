insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	110
  ,'kks.kehitysasialaji.paivahoidon_havainnot_4v_neuvolaan'   -- type_code
  ,'P�iv�hoidon havainnot 4-vuotisneuvolaan'  -- name
  ,''  -- description
  ,'kks.suostumus.paivahoidon_terveiset_4v_neuvolaan'  -- concent_type
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
   800   -- group_id
  ,1   -- sort_order
  ,'P�iv�hoito'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,110
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
   801   -- group_id
  ,2   -- sort_order
  ,'Neuvola'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,110
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
   802   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,800   -- parent_id
  ,110
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
   803   -- group_id
  ,2   -- sort_order
  ,'P�ivitt�iset toiminnot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,800   -- parent_id
  ,110
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
   804   -- group_id
  ,3   -- sort_order
  ,'Motoriset taidot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,800   -- parent_id
  ,110
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
   805   -- group_id
  ,4   -- sort_order
  ,'Kieli ja kommunikaatio'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,800   -- parent_id
  ,110
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
   806   -- group_id
  ,5   -- sort_order
  ,'Sosiaalisuus ja tunneilmaisu'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,800   -- parent_id
  ,110
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
   807   -- group_id
  ,1   -- sort_order
  ,'Neuvolan palaute p�iv�hoitoon'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,801   -- parent_id
  ,110
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
   1100   -- entry_class_id
  ,1   -- sort_order
  ,'P�iv�hoitopaikka:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,802   -- entry_group
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
   1101   -- entry_class_id
  ,2   -- sort_order
  ,'Ty�ntekij�n nimi ja puhelinnumero:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,802   -- entry_group
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
   1102   -- entry_class_id
  ,3   -- sort_order
  ,'P�iv�m��r�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,802   -- entry_group
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
   1103   -- entry_class_id
  ,4   -- sort_order
  ,'Hoitoaika:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kokop�iv�hoito,osap�iv�hoito (alle 5t/pv),vuorohoito'  -- value_spaces
  ,802   -- entry_group
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
   1104   -- entry_class_id
  ,5   -- sort_order
  ,'Sy� omatoimisesti:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,803   -- entry_group
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
   1105   -- entry_class_id
  ,6   -- sort_order
  ,'Pukee ja riisuu itse:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,803   -- entry_group
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
   1106   -- entry_class_id
  ,7   -- sort_order
  ,'K�y omatoimisesti wc:ss�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,803   -- entry_group
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
   1107   -- entry_class_id
  ,8   -- sort_order
  ,'Sopeutuu p�iv�rytmiin ja siirtyy joustavasti toiminnasta toiseen:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,803   -- entry_group
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
   1108   -- entry_class_id
  ,9   -- sort_order
  ,'Unirytmi:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,803   -- entry_group
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
   1109   -- entry_class_id
  ,10   -- sort_order
  ,'Huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,803   -- entry_group
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
   1110   -- entry_class_id
  ,11   -- sort_order
  ,'Piirt�� tunnistettavia asioita (esim. ihmishahmon):'  -- name
  ,'Kuvaa hienomotoristisia taitoja'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,804   -- entry_group
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
   1111   -- entry_class_id
  ,12   -- sort_order
  ,'Leikkaa saksilla suoraa viivaa  pitkin:'  -- name
  ,'Kuvaa hienomotoristisia taitoja'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,804   -- entry_group
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
   1112   -- entry_class_id
  ,13   -- sort_order
  ,'Tunnistaa kehonosia:'  -- name
  ,'Kuvaa karkeamotoristisia taitoja'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,804   -- entry_group
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
   1113   -- entry_class_id
  ,14   -- sort_order
  ,'Liikkuu sujuvasti ja hallitsee tasapainonsa (k�vely, juoksu, hyppiminen):'  -- name
  ,'Kuvaa karkeamotoristisia taitoja'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,804   -- entry_group
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
   1114   -- entry_class_id
  ,15   -- sort_order
  ,'K�tisyys:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'oikea,vasen,ei vakiintunut'  -- value_spaces
  ,804   -- entry_group
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
   1150   -- entry_class_id
  ,15   -- sort_order
  ,'Kyn�ote:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kolmisormi,nyrkki,muu'  -- value_spaces
  ,804   -- entry_group
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
   1115   -- entry_class_id
  ,16   -- sort_order
  ,'Huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,804   -- entry_group
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
   1116   -- entry_class_id
  ,17   -- sort_order
  ,'Osaa kertoa ja selostaa lausein erilaisia asioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,805   -- entry_group
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
   1117   -- entry_class_id
  ,18   -- sort_order
  ,'Ymm�rt�� annettuja  kaksiosaisia ohjeita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,805   -- entry_group
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
   1118   -- entry_class_id
  ,19   -- sort_order
  ,'Puhe on selke�� ja ymm�rrett�v�� (joitakin ��nivirheit� voi olla):'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,805   -- entry_group
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
   1119   -- entry_class_id
  ,20   -- sort_order
  ,'On kiinnostuntu saduista ja kertomuksista:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,805   -- entry_group
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
   1120   -- entry_class_id
  ,21   -- sort_order
  ,'Havannoi ymp�rist�st��n lukum��ri� ja osaa laskea kolmeen:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,805   -- entry_group
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
   1121   -- entry_class_id
  ,22   -- sort_order
  ,'Huomioita:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,805   -- entry_group
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
   1122   -- entry_class_id
  ,23   -- sort_order
  ,'Luottaa itseens� ja omiin kykyihins�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens� aina,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1123   -- entry_class_id
  ,24   -- sort_order
  ,'Luottaa itseens� ja omiin kykyihins�:'  -- name
  ,'Kuvaa itsetuntoa ja tunteita'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1124   -- entry_class_id
  ,25   -- sort_order
  ,'Osallistuu p�iv�kodin toimintoihin:'  -- name
  ,'Kuvaa itsetuntoa ja tunteita'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1125   -- entry_class_id
  ,26   -- sort_order
  ,'Suhtautuu tuttuihin aikuisiin luottavaisesti:'  -- name
  ,'Kertoo asioistaan, ilmaisee toiveitaan ja tarpeitaan'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1126   -- entry_class_id
  ,27   -- sort_order
  ,'Ilmaisee erilaisia tunteita:'  -- name
  ,'Kuvaa itsetuntoa ja tunteita'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1127   -- entry_class_id
  ,28   -- sort_order
  ,'Tuntee my�t�tuntoa ja ottaa vastaan lohdutusta:'  -- name
  ,'Kuvaa itsetuntoa ja tunteita'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1128   -- entry_class_id
  ,29   -- sort_order
  ,'Solmii kaverisuhteita:'  -- name
  ,'Kuvaa itsetuntoa ja tunteita'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1129   -- entry_class_id
  ,30   -- sort_order
  ,'Leikkii yhdess� muiden lasten kanssa:'  -- name
  ,'Kuvaa leikkitaitoja ja suhtautumista toisiin lapsiin'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1130   -- entry_class_id
  ,31   -- sort_order
  ,'Leikkii rooli- ja mielikuvitusleikkej�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1131   -- entry_class_id
  ,32   -- sort_order
  ,'Neuvottelee muiden kanssa leikkitilanteissa:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1132   -- entry_class_id
  ,33   -- sort_order
  ,'Keskittyy yht�jaksoisesti leikkiin v�hint��n 5-10min:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1133   -- entry_class_id
  ,34   -- sort_order
  ,'Keskittyy omaan toimintaansa vapaassa ryhm�tilanteessa:'  -- name
  ,'Kuvaa ty�stkentelytaitoja ja keskittymist�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1134   -- entry_class_id
  ,35   -- sort_order
  ,'Keskittyy toimintaan ohjatussa ryhm�tilanteessa:'  -- name
  ,'Kuvaa ty�stkentelytaitoja ja keskittymist�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1135   -- entry_class_id
  ,36   -- sort_order
  ,'Jaksaa odottaa omaa vuoroaan:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1136   -- entry_class_id
  ,37   -- sort_order
  ,'Motivoituu annetuista teht�vist�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1137   -- entry_class_id
  ,38   -- sort_order
  ,'Toimii arjen s��nt�jen mukaan:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'yleens� aina,vaihtelevasti,ei viel�'  -- value_spaces
  ,806   -- entry_group
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
   1138   -- entry_class_id
  ,39   -- sort_order
  ,'Kuvausta lapsen sosiaalisista taidoista, tunne el�m�st� sek� leikist�:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,806   -- entry_group
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
   1139   -- entry_class_id
  ,40   -- sort_order
  ,''  -- name
  ,'Seurattavaa'  -- description
  ,0   -- multi_value
  ,'SELECTION'  -- data_type
  ,'kyll�,ei'  -- value_spaces
  ,807   -- entry_group
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
   1140   -- entry_class_id
  ,41   -- sort_order
  ,'Jatkosuunnitelma lapsen kehityksen tukemiseksi:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,807   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1100   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1100   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1101   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1101   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1102   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1102   -- entry_class_id
  ,60   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1103   -- entry_class_id
  ,13   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1103   -- entry_class_id
  ,62   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1104   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1104   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1105   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1105   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1106   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1106   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1107   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1107   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1108   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1108   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1109   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1109   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1110   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1110   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1111   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1111   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1112   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1112   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1113   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1113   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1114   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1114   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1150   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1150   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1115   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1115   -- entry_class_id
  ,21   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1116   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1116   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1117   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1117   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1118   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1118   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1119   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1119   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1120   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1120   -- entry_class_id
  ,30   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1121   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1121   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1122   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1122   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1123   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1123   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1124   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1124   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1125   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1125   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1126   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1126   -- entry_class_id
  ,35   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1127   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1127   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1128   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1128   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1129   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1129   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1129   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1130   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1130   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1130   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1131   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1131   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1132   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1132   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1132   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1132   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1133   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1133   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1133   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1133   -- entry_class_id
  ,36   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1134   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1134   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1134  -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1134   -- entry_class_id
  ,36   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1135   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1135   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1135   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1135   -- entry_class_id
  ,36   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1136   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1136   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1136   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1136   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1137   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1137   -- entry_class_id
  ,35   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1137   -- entry_class_id
  ,33   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1137   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1138   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1138   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1139   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1139   -- entry_class_id
  ,53   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1140   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   1140   -- entry_class_id
  ,53   -- tag_id
);