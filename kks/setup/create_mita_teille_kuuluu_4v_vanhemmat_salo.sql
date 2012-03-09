insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	70
  ,'kks.kehitysasialaji.mita_teille_kuuluu_4v_vanhemmat'   -- type_code
  ,'Mit� teille kuuluu? 4-vuotiaan lapsen vanhemmille'  -- name
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
   600   -- group_id
  ,1   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,70
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
   603   -- group_id
  ,0   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,600   -- parent_id
  ,70
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
   601   -- group_id
  ,2   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,600   -- parent_id
  ,70
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
   602   -- group_id
  ,3   -- sort_order
  ,'Elintavat'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'guardian' -- accountable
  ,600   -- parent_id
  ,70
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
   700   -- entry_class_id
  ,1   -- sort_order
  ,'Onko perheess�nne tapahtunut l�hiaikoina jotain, mik� on vienyt paljon voimavarojanne?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   701   -- entry_class_id
  ,2   -- sort_order
  ,'Mitk� tilanteet tuntuvat mukavilta lapsen kanssa?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   702   -- entry_class_id
  ,3   -- sort_order
  ,'Mitk� ovat lapsesi parhaat puolet?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   703   -- entry_class_id
  ,4   -- sort_order
  ,'Tuottavatko jotkin arkip�iv�n tilanteet teill� pulmia lasten kanssa?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   704   -- entry_class_id
  ,5   -- sort_order
  ,'Oletko huolissasi jostain lapsen kehitykseen liittyv�st� asiasta?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   705   -- entry_class_id
  ,6   -- sort_order
  ,'Miten p�iv�hoito sujuu? Miten lapsi toimii ryhm�ss�?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   706   -- entry_class_id
  ,7   -- sort_order
  ,'Miten perheess�nne ratkaistaan ristiriitatilanteet?'  -- name
  ,'lasten ja aikuisten v�liset, aikuisten keskin�iset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   707   -- entry_class_id
  ,8   -- sort_order
  ,'Mit� asioita teette yhdess� koko perheen kanssa?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,601   -- entry_group
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
   708   -- entry_class_id
  ,9   -- sort_order
  ,'Tupakoidaanko perheess�nne?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECTION'  -- data_type
  ,'kyll�,ei'  -- value_spaces
  ,602   -- entry_group
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
   709   -- entry_class_id
  ,10   -- sort_order
  ,'Mik�li perheess�nne tupakoidaan, niin miss� tupakointi tapahtuu?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,602   -- entry_group
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
   710   -- entry_class_id
  ,11   -- sort_order
  ,'Mik�li perheess�nne tupakoidaan, niin kuinka monta savuketta poltatte vuorokaudessa (�iti/is�/muu henkil�)?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,602   -- entry_group
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
   711   -- entry_class_id
  ,12   -- sort_order
  ,'T�ytt�m�nne Audit-lomakkeen pistem��r�t (�iti/is�/muu henkil�)?'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,602   -- entry_group
);

-- NEW FIELDS

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
   712   -- entry_class_id
  ,13   -- sort_order
  ,'Syntym�aika:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   713   -- entry_class_id
  ,14   -- sort_order
  ,'�iti/huoltaja:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   714   -- entry_class_id
  ,15   -- sort_order
  ,'�iti/huoltaja, ty�paikka ja puhelinnumero:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   715   -- entry_class_id
  ,16   -- sort_order
  ,'�iti/huoltaja, �idinkieli:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   716   -- entry_class_id
  ,17   -- sort_order
  ,'Is�/huoltaja:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   717   -- entry_class_id
  ,18   -- sort_order
  ,'Is�/huoltaja, ty�paikka ja puhelinnumero:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   718   -- entry_class_id
  ,19   -- sort_order
  ,'Is�/huoltaja, �idinkieli:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
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
   719   -- entry_class_id
  ,20   -- sort_order
  ,'Perhesuhde:'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'avioliitto,avoliitto,muu'  -- value_spaces
  ,603   -- entry_group
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
   720   -- entry_class_id
  ,21   -- sort_order
  ,'Sisarukset ja muut samassa taloudessa asuvat (nimi ja syntym�vuosi):'  -- name
  ,''  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,603   -- entry_group
);

-- NEW TAGS

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   712   -- entry_class_id
  ,56   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   713   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   714   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   715   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   716   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   717   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   718   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   719   -- entry_class_id
  ,56   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   720   -- entry_class_id
  ,56   -- tag_id
);

-- TAGS END

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   700   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   700   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   701   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   701   -- entry_class_id
  ,49   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   702   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   702   -- entry_class_id
  ,49   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   703   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   703   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   703   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   704   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   704   -- entry_class_id
  ,48   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   704   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   705   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   705   -- entry_class_id
  ,61   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   706   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   706   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   707   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   707   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   708   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   708   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   709   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   709   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   710   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   710   -- entry_class_id
  ,48   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   711   -- entry_class_id
  ,13   -- tag_id
);
insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   711   -- entry_class_id
  ,48   -- tag_id
);
