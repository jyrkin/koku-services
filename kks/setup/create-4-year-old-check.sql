insert into kks_collection_class (
  id
  ,type_code
  ,name
  ,description
  ,consent_type
) VALUES (
	2
  ,'kks.kehitysasialaji.4-vuotiaan_neuvolatarkastus'   -- type_code
  ,'4-vuotiaan neuvolatarkastus'  -- name
  ,''  -- description
  ,'kks.suostumus.4-vuotiaan_neuvolatarkastus'  -- concent_type
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
   21   -- group_id
  ,2   -- sort_order
  ,'P�iv�hoito'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,2
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
   22   -- group_id
  ,3   -- sort_order
  ,'Neuvola'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,2
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
   24   -- group_id
  ,1   -- sort_order
  ,'P�ivitt�istoiminnot'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,21   -- parent_id
  ,2
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
   25   -- group_id
  ,2   -- sort_order
  ,'Liikkuminen ja hahmottaminen'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,21   -- parent_id
  ,2
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
   26   -- group_id
  ,3   -- sort_order
  ,'Kieli ja kommunikaatio'  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,21   -- parent_id
  ,2
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
   27   -- group_id
  ,4   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'daycareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,21   -- parent_id
  ,2
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
   28   -- group_id
  ,2   -- sort_order
  ,''  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,22   -- parent_id
  ,2
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
   29   -- group_id
  ,1   -- sort_order
  ,'Mittaukset'  -- name
  ,''  -- description
  ,'healthcareregistry'  -- register
  ,'municipal_employee' -- accountable
  ,22   -- parent_id
  ,2
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
   57   -- entry_class_id
  ,8   -- sort_order
  ,'Pukee ja riisuu itse?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,24   -- entry_group
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
   58   -- entry_class_id
  ,9   -- sort_order
  ,'Sy� siististi?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,24   -- entry_group
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
   59   -- entry_class_id
  ,10   -- sort_order
  ,'K�y omatoimisesti wc:ss�?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,24   -- entry_group
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
   60   -- entry_class_id
  ,11   -- sort_order
  ,'Hahmottaa hoitopaikan hoitorytmin?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,24   -- entry_group
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
   61   -- entry_class_id
  ,12   -- sort_order
  ,'Siirtyminen toiminnasta toiseen onnistuu?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,opettelee,ei viel�'  -- value_spaces
  ,24   -- entry_group
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
   62   -- entry_class_id
  ,13   -- sort_order
  ,'P�iv�lepo?'  -- name
  ,'kuvaa p�ivitt�istoimintojen sujumista'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Nukkuu p�ivitt�in,Satunnaisesti,Ei nuku p�iv�unia'  -- value_spaces
  ,24   -- entry_group
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
   93   -- entry_class_id
  ,14   -- sort_order
  ,'Juoksee sujuvasti?'  -- name
  ,'kuvaa fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   63   -- entry_class_id
  ,15   -- sort_order
  ,'K�velee kapealla penkill� / narua pitkin?'  -- name
  ,'kuvaa fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   64   -- entry_class_id
  ,16   -- sort_order
  ,'Hypp�� tasajalkahyppy�?'  -- name
  ,'kuvaa fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   65   -- entry_class_id
  ,17   -- sort_order
  ,'K�velee portaita yl�s ja alas vuorotahtiin?'  -- name
  ,'kuvaa fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   66   -- entry_class_id
  ,18   -- sort_order
  ,'Kiipeilee?'  -- name
  ,'kuvaa fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   67   -- entry_class_id
  ,19   -- sort_order
  ,'Tunnistaa ja nime�� kehonosia?'  -- name
  ,'kuvaa psyykkist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   68   -- entry_class_id
  ,20   -- sort_order
  ,'Piirt�� tunnistettavia asioita?'  -- name
  ,'kuvaa psyykkist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   69   -- entry_class_id
  ,21   -- sort_order
  ,'Leikkaa saksilla?'  -- name
  ,'kuvaa psyykkist� ja fyysist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'kyll�,vaihtelevasti,ei viel�'  -- value_spaces
  ,25   -- entry_group
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
   70   -- entry_class_id
  ,22   -- sort_order
  ,'Kokoaa palapelej�?'  -- name
  ,'kuvaa psyykkist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'n.12 palaa,n.20 palaa,yli 20 palaa'  -- value_spaces
  ,25   -- entry_group
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
   71   -- entry_class_id
  ,23   -- sort_order
  ,'K�tisyys?'  -- name
  ,'kuvaa fyysist� ja psyykkist� kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Oikea,Vasen,Vaihtaen'  -- value_spaces
  ,25   -- entry_group
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
   72   -- entry_class_id
  ,24   -- sort_order
  ,'Ottaa ja s�ilytt�� katsekontaktin vuorovaikutuksessa?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   73   -- entry_class_id
  ,25   -- sort_order
  ,'Osaa kuunnella vastavuoroisesti?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   74   -- entry_class_id
  ,26   -- sort_order
  ,'Toimii ohjeiden mukaan?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   75   -- entry_class_id
  ,27   -- sort_order
  ,'On kiinnostunut saduista ja kertomuksista?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   76   -- entry_class_id
  ,28   -- sort_order
  ,'Puhe on selke�� ja ymm�rrett�v��?'  -- name
  ,'kuvaa kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   77   -- entry_class_id
  ,29   -- sort_order
  ,'Huomioita puheesta'  -- name
  ,'esim. ��nivirheet, �nkytys, k�heys, jne'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,26   -- entry_group
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
   78   -- entry_class_id
  ,30   -- sort_order
  ,'Ymm�rt�� lukum��rien 1-4 vastaavuuden?'  -- name
  ,'kuvaa matemaattisten taitojen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   79   -- entry_class_id
  ,31   -- sort_order
  ,'Tunnistaa ja nime�� perusv�rit?'  -- name
  ,'kuvaa kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   80   -- entry_class_id
  ,32   -- sort_order
  ,'Osaa kertoa pieni� tarinoita / osaa kertoa tapahtuneista asioista?'  -- name
  ,'kuvaa kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'SELECT'  -- data_type
  ,'Yleens�,Vaihtelevasti,Ei viel�'  -- value_spaces
  ,26   -- entry_group
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
   81   -- entry_class_id
  ,33   -- sort_order
  ,'Vahvuudet ja vaikeudet leikeiss�?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,27   -- entry_group
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
   82   -- entry_class_id
  ,34   -- sort_order
  ,'Vahvuudet ja vaikeudet sosiaalisissa tilanteissa?'  -- name
  ,'kuvaa vuorovaikutuksen ja kielen kehityst�'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,27   -- entry_group
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
   83   -- entry_class_id
  ,35   -- sort_order
  ,'Terveiset neuvolaan'  -- name
  ,'terveiset neuvolaan'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,27   -- entry_group
);

-- Neuvola

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
   84   -- entry_class_id
  ,35   -- sort_order
  ,'Yleist� neuvolak�ynnin sujumisesta'  -- name
  ,'kuvaa neuvolak�ynnin sujumisen'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,28   -- entry_group
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
   85   -- entry_class_id
  ,36   -- sort_order
  ,'Onko huolenaiheita ja miten mahdolliset huolenaiheet on huomioitu?'  -- name
  ,'mm. pelot, arkuus, sosiaaliset taidot, puhe, motoriikka, siisteyskasvatus, perhetilanne, kasvatuskysymykset'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,28   -- entry_group
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
   86   -- entry_class_id
  ,37   -- sort_order
  ,'Neuvola ohjannut jatkoselvityksiin'  -- name
  ,'l�hetteet'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,28   -- entry_group
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
   87   -- entry_class_id
  ,38   -- sort_order
  ,'Terveiset p�iv�hoitoon'  -- name
  ,'neuvolan terveiset p�iv�hoidolle'  -- description
  ,0   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,28   -- entry_group
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
   88   -- entry_class_id
  ,39   -- sort_order
  ,'P��nymp�rys'  -- name
  ,'lapsen p��nymp�rys senttein�'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,29   -- entry_group
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
   89   -- entry_class_id
  ,40   -- sort_order
  ,'Pituus'  -- name
  ,'lapsen pituus senttein�'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,29   -- entry_group
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
   90   -- entry_class_id
  ,41   -- sort_order
  ,'Paino'  -- name
  ,'lapsen paino kiloina'  -- description
  ,0   -- multi_value
  ,'TEXT'  -- data_type
  ,''  -- value_spaces
  ,29   -- entry_group
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
   91   -- entry_class_id
  ,42   -- sort_order
  ,'Kommentit, havainnot ja tavoitteet'  -- name
  ,'neuvolan terveiset p�iv�hoidolle'  -- description
  ,1   -- multi_value
  ,'FREE_TEXT'  -- data_type
  ,''  -- value_spaces
  ,28   -- entry_group
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   57   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   57   -- entry_class_id
  ,21   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   57   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   57   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   58   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   58   -- entry_class_id
  ,21   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   58   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   58   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   59   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   59   -- entry_class_id
  ,21   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   59   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   59   -- entry_class_id
  ,33   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   60   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   60   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   60   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   60   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   61   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   61   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   61   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   61   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   62   -- entry_class_id
  ,33   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   62   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   93   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   93   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   93   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   93   -- entry_class_id
  ,29   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   93   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   63   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   63   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   63   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   63   -- entry_class_id
  ,29   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   63   -- entry_class_id
  ,23   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   64   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   64   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   64   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   64   -- entry_class_id
  ,29   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   64   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   65   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   65   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   65   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   65   -- entry_class_id
  ,29   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   65   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   66   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   66   -- entry_class_id
  ,20   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   66   -- entry_class_id
  ,24   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   66   -- entry_class_id
  ,29   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   66   -- entry_class_id
  ,23   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   67   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   67   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   68   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   68   -- entry_class_id
  ,32   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   69   -- entry_class_id
  ,12   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   69   -- entry_class_id
  ,32   -- tag_id
);


insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   69   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   70   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   70   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   70   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   71   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   71   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   72   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   72   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   72   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   72   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   73   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   73   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   73   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   73   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   74   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   74   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   74   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   75   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   75   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   75   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   75   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   76   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   76   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   76   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   76   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   77   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   77   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   77   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   77   -- entry_class_id
  ,22   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   78   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   78   -- entry_class_id
  ,30   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   79   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   79   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   80   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   80   -- entry_class_id
  ,25   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   80   -- entry_class_id
  ,36   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   80   -- entry_class_id
  ,37   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   80   -- entry_class_id
  ,32   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   81   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   81   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   81   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   81   -- entry_class_id
  ,28   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   81   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   82   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   82   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   82   -- entry_class_id
  ,27   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   82   -- entry_class_id
  ,35   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   83   -- entry_class_id
  ,53   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   84   -- entry_class_id
  ,12   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   85   -- entry_class_id
  ,11   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   85   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   86   -- entry_class_id
  ,52   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   86   -- entry_class_id
  ,40   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   87   -- entry_class_id
  ,53   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   88   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   88   -- entry_class_id
  ,14   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   89   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   89   -- entry_class_id
  ,14   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   90   -- entry_class_id
  ,59   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   90   -- entry_class_id
  ,14   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   91   -- entry_class_id
  ,54   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   91   -- entry_class_id
  ,57   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   91   -- entry_class_id
  ,58   -- tag_id
);

insert into kks_entry_class_tags (
   entry_class_id
  ,tag_id
) VALUES (
   91   -- entry_class_id
  ,12   -- tag_id
);


