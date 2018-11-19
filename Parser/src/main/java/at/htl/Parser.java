package at.htl;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void main(String[] args) {
        //alle zahlungen (vom csv-file) werden in dieser liste gespeichert (und persistiert in der DB)
        List<Zahlung> zahlungen = new ArrayList<>(); // besser als linked list
        String fileContent = "\"Buchungsdatum;\"\"Partnername\"\";\"\"Partner IBAN\"\";\"\"Partner BIC\"\";\"\"Partner Kontonummer\"\";\"\"Partner Bank-Code (BLZ)\"\";\"\"Betrag\"\";\"\"Währung\"\";\"\"Buchungstext\"\";\"\"Ersterfassungsreferenz\"\";\"\"Notiz\"\";\"\"Valutadatum\"\"\"\n" +
                "\"02.01.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"375,00\"\";\"\"EUR\"\";\"\"SB-Eigenerlag K1 S72182 30.12/10:32\"\";\"\"203201712302ALH-72182K325501\"\";\"\"\"\";\"\"30.12.2017\"\"\"\n" +
                "\"02.01.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"30,00\"\";\"\"EUR\"\";\"\"SB-Eigenerlag K1 S72182 30.12/11:42\"\";\"\"203201712302ALH-72182L420701\"\";\"\"\"\";\"\"30.12.2017\"\"\"\n" +
                "\"03.01.2018;\"\"Zalando SE\"\";\"\"DE86210700200123010101\"\";\"\"DEUTDEHH210\"\";\"\"\"\";\"\"\"\";\"\"11,95\"\";\"\"EUR\"\";\"\"10606014703021 ZALANDO SE Zalando SE 011180541449\"\";\"\"201001801032AEI-48XL18029472\"\";\"\"\"\";\"\"03.01.2018\"\"\"\n" +
                "\"05.01.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-5351/2018 spusu MDID:spusu218986\"\";\"\"1200018010310082950418820765\"\";\"\"\"\";\"\"05.01.2018\"\"\"\n" +
                "\"08.01.2018;\"\"Global Collect BV\"\";\"\"AT571910000040731001\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-3,95\"\";\"\"EUR\"\";\"\"902117530239 430022-26-44 Global Collect BV\"\";\"\"203201801052CDC-00KVY9EO9HZC\"\";\"\"\"\";\"\"08.01.2018\"\"\"\n" +
                "\"08.01.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"Information gemäß VKrG per 05.01.2018 Sollzinsen: 5,87500 % Verzugszinsen: 4,75000 % Eine Änderung der Konditionen kann entsprechend den in den Allgemeinen Geschäftsbedingungen enthaltenen Änderungsklauseln erfolgen.\"\";\"\"203201801062AAU-0050TYYGFCDC\"\";\"\"\"\";\"\"08.01.2018\"\"\"\n" +
                "\"11.01.2018;\"\"Michael Frech\"\";\"\"AT772032032504205943\"\";\"\"ASPKAT2LXXX\"\";\"\"32504205943\"\";\"\"20320\"\";\"\"922,48\"\";\"\"EUR\"\";\"\"SEPA-Gutschrift Michael Frech\"\";\"\"203201801112AIP-00I4FKNDMDAW\"\";\"\"\"\";\"\"11.01.2018\"\"\"\n" +
                "\"15.01.2018;\"\"Iraschek Sonja\"\";\"\"AT123400000000193664\"\";\"\"RZOOAT2LXXX\"\";\"\"\"\";\"\"\"\";\"\"100,00\"\";\"\"EUR\"\";\"\"Taschengeld und so Iraschek Sonja 4532 Rohr im Kremstal, Linzer Straß\"\";\"\"34000180112-4301290-0000538\"\";\"\"\"\";\"\"15.01.2018\"\"\"\n" +
                "\"17.01.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"Guthaben auf Girokonten und Spareinlagen sind gemäß Einlagensicherungs- und Anlegerentschädigungsgesetz erstattungsfähig. Nähere Informationen dazu entnehmen Sie bitte dem 'Informationsbogen für den Einleger' - abholbereit in jeder Filiale bzw. abrufbar unter: www.erstebank.at/einlagensicherung und www.sparkasse.at/einlagensicherung.\"\";\"\"203201801172AAU-005F5NZSFD4W\"\";\"\"\"\";\"\"17.01.2018\"\"\"\n" +
                "\"19.01.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG D\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"5,52\"\";\"\"EUR\"\";\"\"D888X7QYS51JTSJ0JEX Amazon Prime Ge AMAZON EU S.A R.L., NIEDERLASSUNG D 0672971264107819 buehr 0672971264107819\"\";\"\"201001801192AEI-30XYN8000681\"\";\"\"\"\";\"\"19.01.2018\"\"\"\n" +
                "\"24.01.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-10,00\"\";\"\"EUR\"\";\"\"AUTOMAT 00041283 K1 24.01. 14:53\"\";\"\"203201801242ALB-00EBFQUM8LJ4\"\";\"\"\"\";\"\"24.01.2018\"\"\"\n" +
                "\"30.01.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-8,05\"\";\"\"EUR\"\";\"\"305-6820854-3880300 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 1546359300697231\"\";\"\"201001801292AEI-12XJTK040605\"\";\"\"\"\";\"\"30.01.2018\"\"\"\n" +
                "\"06.02.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-60261/2018 spusu MDID:spusu218986\"\";\"\"1200018020208265087716432757\"\";\"\"\"\";\"\"06.02.2018\"\"\"\n" +
                "\"20.02.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-35,99\"\";\"\"EUR\"\";\"\"305-8539332-8127569 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 1425177888369991\"\";\"\"201001802192AEI-11XB7I003796\"\";\"\"\"\";\"\"20.02.2018\"\"\"\n" +
                "\"20.02.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-38,70\"\";\"\"EUR\"\";\"\"305-7047214-8796303 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 1639893794096455\"\";\"\"201001802192AEI-11XB7I006774\"\";\"\"\"\";\"\"20.02.2018\"\"\"\n" +
                "\"06.03.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-118709/2018 spusu MDID:spusu218986\"\";\"\"1200018030212015034817234586\"\";\"\"\"\";\"\"06.03.2018\"\"\"\n" +
                "\"14.03.2018;\"\"Adyen Client Mgt Foundation\"\";\"\"DE85300600100000021573\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-11,99\"\";\"\"EUR\"\";\"\"4415210140782019 UdemyEU Adyen Client Mgt Foundation\"\";\"\"203201803142CDC-008FMOWU2CDC\"\";\"\"\"\";\"\"14.03.2018\"\"\"\n" +
                "\"16.03.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-16,49\"\";\"\"EUR\"\";\"\"305-8847496-7341912 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 5978617234582133\"\";\"\"201001803152AEI-08XGOM009084\"\";\"\"\"\";\"\"16.03.2018\"\"\"\n" +
                "\"19.03.2018;\"\"Adyen Client Mgt Foundation\"\";\"\"DE85300600100000021573\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-11,99\"\";\"\"EUR\"\";\"\"1815213709523941 UdemyEU Adyen Client Mgt Foundation\"\";\"\"203201803192CDC-003CNCHMUCZ4\"\";\"\"\"\";\"\"19.03.2018\"\"\"\n" +
                "\"23.03.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-22,18\"\";\"\"EUR\"\";\"\"302-2366623-2283513 Amazon.de 74478 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 79179764722\"\";\"\"201001803222AEI-10XZAS003793\"\";\"\"\"\";\"\"23.03.2018\"\"\"\n" +
                "\"23.03.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-400,00\"\";\"\"EUR\"\";\"\"AUTOMAT 00041993 K1 23.03. 14:07\"\";\"\"203201803232ALB-00DVAI0C60V4\"\";\"\"\"\";\"\"23.03.2018\"\"\"\n" +
                "\"27.03.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-16,99\"\";\"\"EUR\"\";\"\"302-9456334-7660331 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 1200136089075144\"\";\"\"201001803262AEI-10X43G069215\"\";\"\"\"\";\"\"27.03.2018\"\"\"\n" +
                "\"28.03.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"16,49\"\";\"\"EUR\"\";\"\"305-8847496-7341912 AMZ.3B-IT Ltd 7 AMAZON PAYMENTS EUROPE S.C.A. 7319031500255035 319031500255035\"\";\"\"201001803282AEI-59XD42017678\"\";\"\"\"\";\"\"28.03.2018\"\"\"\n" +
                "\"30.03.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"* Abschlussbuchung per 31.03.2018 ** Reklamationen bitte binnen 2 Monaten\"\";\"\"203201803312AB1-P32202569964\"\";\"\"\"\";\"\"31.03.2018\"\"\"\n" +
                "\"30.03.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"9971971986\"\";\"\"20320\"\";\"\"0,02\"\";\"\"EUR\"\";\"\"Habenzinsen\"\";\"\"203201803312AB1-P32202569964\"\";\"\"\"\";\"\"31.03.2018\"\"\"\n" +
                "\"30.03.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"9971971986\"\";\"\"20320\"\";\"\"-0,01\"\";\"\"EUR\"\";\"\"Kapitalertragsteuer\"\";\"\"203201803312AB1-P32202569964\"\";\"\"\"\";\"\"31.03.2018\"\"\"\n" +
                "\"03.04.2018;\"\"Ing. Martin Mandlmayr Fahrschule\"\";\"\"AT432032022600000991\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-847,00\"\";\"\"EUR\"\";\"\"18/0300 Ing. Martin Mandlmayr Fahrschule\"\";\"\"203201803302AIP-00JP3GGLUAYG\"\";\"\"\"\";\"\"03.04.2018\"\"\"\n" +
                "\"06.04.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-178683/2018 spusu MDID:spusu218986\"\";\"\"1200018040408321256926757466\"\";\"\"\"\";\"\"06.04.2018\"\"\"\n" +
                "\"10.04.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-48,35\"\";\"\"EUR\"\";\"\"302-6005112-4439558 Amazon.de 11801 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 96916354134\"\";\"\"201001804092AEI-18X6Y9014218\"\";\"\"\"\";\"\"10.04.2018\"\"\"\n" +
                "\"20.04.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-31,10\"\";\"\"EUR\"\";\"\"305-2716868-2926728 Amazon.de 53299 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 91939423267\"\";\"\"201001804192AEI-05X3BT020804\"\";\"\"\"\";\"\"20.04.2018\"\"\"\n" +
                "\"23.04.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"200,00\"\";\"\"EUR\"\";\"\"SB-Eigenerlag K1 S72182 21.04/11:23\"\";\"\"203201804212ALH-72182L234901\"\";\"\"\"\";\"\"21.04.2018\"\"\"\n" +
                "\"03.05.2018;\"\"PayPal (Europe) S.a.r.l. et Cie., S.C.A.\"\";\"\"DE88500700100175526303\"\";\"\"DEUTDEFFXXX\"\";\"\"\"\";\"\"\"\";\"\"0,11\"\";\"\"EUR\"\";\"\"YYR5ELVDTKQ2JHDL PAYPAL PayPal (Europe) S.a.r.l. et Cie., S\"\";\"\"201001805032AEI-59X5IE008000\"\";\"\"\"\";\"\"03.05.2018\"\"\"\n" +
                "\"03.05.2018;\"\"PayPal (Europe) S.a.r.l. et Cie., S.C.A.\"\";\"\"DE88500700100175526303\"\";\"\"DEUTDEFFXXX\"\";\"\"\"\";\"\"\"\";\"\"0,13\"\";\"\"EUR\"\";\"\"YYR5ELPPWT7K9HDL PAYPAL PayPal (Europe) S.a.r.l. et Cie., S\"\";\"\"201001805032AEI-59X5IE008001\"\";\"\"\"\";\"\"03.05.2018\"\"\"\n" +
                "\"04.05.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-241510/2018 spusu MDID:spusu218986\"\";\"\"1200018050215032179230020444\"\";\"\"\"\";\"\"04.05.2018\"\"\"\n" +
                "\"04.05.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-20,55\"\";\"\"EUR\"\";\"\"305-8242403-1870763 Amazon.de 16183 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 59698274935\"\";\"\"201001805032AEI-09X7XW005496\"\";\"\"\"\";\"\"04.05.2018\"\"\"\n" +
                "\"04.05.2018;\"\"WKV prepaid GmbH\"\";\"\"AT703500000016056723\"\";\"\"\"\";\"\"\"\";\"\"20320\"\";\"\"-25,00\"\";\"\"EUR\"\";\"\"3E8113RC WERTKARTENVERKAUF WKV prepaid GmbH\"\";\"\"203201805042CDC-00G3O31Y6R60\"\";\"\"\"\";\"\"04.05.2018\"\"\"\n" +
                "\"07.05.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-20,00\"\";\"\"EUR\"\";\"\"SB-Auszahlung K1 S71729 07.05/14:09\"\";\"\"203201805072ALH-71729O091101\"\";\"\"\"\";\"\"07.05.2018\"\"\"\n" +
                "\"08.05.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,95\"\";\"\"EUR\"\";\"\"305-7165117-4401950 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 8374962276668444\"\";\"\"201001805072AEI-03XETO003325\"\";\"\"\"\";\"\"08.05.2018\"\"\"\n" +
                "\"08.05.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-13,24\"\";\"\"EUR\"\";\"\"305-4785024-4497154 Amazon .Mktplce AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV EU-DE 8931814523043066\"\";\"\"201001805072AEI-10XGIX012632\"\";\"\"\"\";\"\"08.05.2018\"\"\"\n" +
                "\"11.05.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-100,00\"\";\"\"EUR\"\";\"\"AUTOMAT 04472224 K1 09.05. 19:31\"\";\"\"203201805092ALB-00J0UXN5L7W0\"\";\"\"\"\";\"\"09.05.2018\"\"\"\n" +
                "\"14.05.2018;\"\"AMAZON INSTANT VIDEO GERMANY GMBH\"\";\"\"DE07300308800013011001\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-5,98\"\";\"\"EUR\"\";\"\"D01-9380730-7192636 Amazon Digital AMAZON INSTANT VIDEO GERMANY GMBH MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV Video 1130198919247998\"\";\"\"201001805112AEI-07XRX8002423\"\";\"\"\"\";\"\"14.05.2018\"\"\"\n" +
                "\"16.05.2018;\"\"Stg Derdengelden BuckarooStg Derden\"\";\"\"DE85700111100002825503\"\";\"\"\"\";\"\"\"\";\"\"20320\"\";\"\"-15,00\"\";\"\"EUR\"\";\"\"52312306 Guthaben.de 39186157 11557 Stg Derdengelden BuckarooStg Derden 4-238082-5AFC51D9-2D32\"\";\"\"203201805162CDC-00H6Z148HOS0\"\";\"\"\"\";\"\"16.05.2018\"\"\"\n" +
                "\"06.06.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-308242/2018 spusu MDID:spusu218986\"\";\"\"1200018060414391686835257593\"\";\"\"\"\";\"\"06.06.2018\"\"\"\n" +
                "\"07.06.2018;\"\"BILLA 4060\"\";\"\"\"\";\"\"\"\";\"\"77319989\"\";\"\"20320\"\";\"\"-1,39\"\";\"\"EUR\"\";\"\"BILLA 4060 4060 K1 07.06. 07:44\"\";\"\"203201806072ALB-007BTA1UM4I8\"\";\"\"\"\";\"\"07.06.2018\"\"\"\n" +
                "\"29.06.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"* Abschlussbuchung per 30.06.2018 ** Reklamationen bitte binnen 2 Monaten\"\";\"\"203201806302AB1-P32202569964\"\";\"\"\"\";\"\"30.06.2018\"\"\"\n" +
                "\"05.07.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-377356/2018 spusu MDID:spusu218986\"\";\"\"1200018070312034037337250730\"\";\"\"\"\";\"\"05.07.2018\"\"\"\n" +
                "\"13.07.2018;\"\"Iraschek Sonja\"\";\"\"AT123400000000193664\"\";\"\"RZOOAT2LXXX\"\";\"\"\"\";\"\"\"\";\"\"200,00\"\";\"\"EUR\"\";\"\"Taschengeld und so Iraschek Sonja Mama\"\";\"\"34000180712-9304669-0003310\"\";\"\"\"\";\"\"13.07.2018\"\"\"\n" +
                "\"16.07.2018;\"\"OOE Verkehrsverbund\"\";\"\"AT651500000721402394\"\";\"\"OBKLAT2L\"\";\"\"\"\";\"\"15000\"\";\"\"-69,00\"\";\"\"EUR\"\";\"\"000013917232 SLF1341323 SLF: K.-Nr. OOE Verkehrsverbund\"\";\"\"203201807142AIL-00EC9C8TNSQO\"\";\"\"\"\";\"\"16.07.2018\"\"\"\n" +
                "\"30.07.2018;\"\"FABASOFT AUSTRIA GMBH\"\";\"\"AT301500000711297564\"\";\"\"OBKLAT2LXXX\"\";\"\"\"\";\"\"\"\";\"\"634,98\"\";\"\"EUR\"\";\"\"Lohn/Gehalt 00011099/201807 FABASOFT AUSTRIA GMBH Honauerstrasse 4\"\";\"\"1500018072721820880310310243\"\";\"\"\"\";\"\"30.07.2018\"\"\"\n" +
                "\"06.08.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-448672/2018 spusu MDID:spusu218986\"\";\"\"1200018080211432824146398965\"\";\"\"\"\";\"\"06.08.2018\"\"\"\n" +
                "\"14.08.2018;\"\"Ing, Martin Mandlmayr Fahrschule Mitterweg 22, 4550 Kremsmünster\"\";\"\"AT432032022600000991\"\";\"\"\"\";\"\"\"\";\"\"20320\"\";\"\"-270,00\"\";\"\"EUR\"\";\"\"Rg. Nr. 18/1018 Ing, Martin Mandlmayr Fahrschule Mi\"\";\"\"203201808142AIP-008XWMG3MN74\"\";\"\"\"\";\"\"14.08.2018\"\"\"\n" +
                "\"30.08.2018;\"\"FABASOFT AUSTRIA GMBH\"\";\"\"AT301500000711297564\"\";\"\"OBKLAT2LXXX\"\";\"\"\"\";\"\"\"\";\"\"1.553,24\"\";\"\"EUR\"\";\"\"Lohn/Gehalt 00011099/201808 FABASOFT AUSTRIA GMBH Honauerstrasse 4\"\";\"\"1500018082921824180310093574\"\";\"\"\"\";\"\"30.08.2018\"\"\"\n" +
                "\"06.09.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-522545/2018 spusu MDID:spusu218986\"\";\"\"1200018090409434027447234170\"\";\"\"\"\";\"\"06.09.2018\"\"\"\n" +
                "\"12.09.2018;\"\"HTBLA Leonding, 4060 Leonding\"\";\"\"AT100100000005390478\"\";\"\"\"\";\"\"\"\";\"\"01000\"\";\"\"-300,00\"\";\"\"EUR\"\";\"\"Romreise 4BHIF Frech Michael HTBLA Leonding, 4060 Leonding\"\";\"\"203201809122AIP-00I24JCBYDAW\"\";\"\"\"\";\"\"12.09.2018\"\"\"\n" +
                "\"20.09.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-22,62\"\";\"\"EUR\"\";\"\"305-8255018-0079510 Amazon.de 11632 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 61603751109\"\";\"\"201001809192AEI-08XXRE005499\"\";\"\"\"\";\"\"20.09.2018\"\"\"\n" +
                "\"21.09.2018;\"\"Bezirkshauptmannschaft\"\";\"\"AT232032020000001808\"\";\"\"\"\";\"\"\"\";\"\"20320\"\";\"\"-173,10\"\";\"\"EUR\"\";\"\"881809579507 Bezirkshauptmannschaft\"\";\"\"203201809212ADR-025250001181\"\";\"\"\"\";\"\"21.09.2018\"\"\"\n" +
                "\"24.09.2018;\"\"AVANTI 3506/ ROHR IM K\"\";\"\"\"\";\"\"\"\";\"\"77319989\"\";\"\"20320\"\";\"\"-49,40\"\";\"\"EUR\"\";\"\"POS 49,40 AT K1 23.09. 16:31 AVANTI 3506/ ROHR IM K ROHR IM KREMS 4532 040\"\";\"\"203201809242ALB-00CBXK91V2WO\"\";\"\"\"\";\"\"23.09.2018\"\"\"\n" +
                "\"24.09.2018;\"\"Michael Frech\"\";\"\"AT772032032504205943\"\";\"\"ASPKAT2LXXX\"\";\"\"\"\";\"\"20320\"\";\"\"-1.000,00\"\";\"\"EUR\"\";\"\"George-Überweisung Michael Frech\"\";\"\"203201809242AIP-00G2YANC4QNK\"\";\"\"\"\";\"\"24.09.2018\"\"\"\n" +
                "\"24.09.2018;\"\"Adyen B.V.\"\";\"\"DE25700111105100580006\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-11,99\"\";\"\"EUR\"\";\"\"1515378042492303 UdemyEU 23931-2670 Adyen B.V. 82-5BA907D9-F64E\"\";\"\"203201809242CDC-00H9GHMONRSW\"\";\"\"\"\";\"\"24.09.2018\"\"\"\n" +
                "\"25.09.2018;\"\"AMAZON PAYMENTS EUROPE S.C.A.\"\";\"\"DE87300308801908262006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-142,52\"\";\"\"EUR\"\";\"\"305-4782189-5859566 AMZN Mktp DE 55 AMAZON PAYMENTS EUROPE S.C.A. MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 03782039396668\"\";\"\"201001809242AEI-10X728014892\"\";\"\"\"\";\"\"25.09.2018\"\"\"\n" +
                "\"26.09.2018;\"\"Leon Schlömmer\"\";\"\"AT902081500004823878\"\";\"\"\"\";\"\"\"\";\"\"20815\"\";\"\"-8,80\"\";\"\"EUR\"\";\"\"George-Überweisung Leon Schlömmer\"\";\"\"203201809262AIP-007ZW4H91OMO\"\";\"\"\"\";\"\"26.09.2018\"\"\"\n" +
                "\"28.09.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"* Abschlussbuchung per 30.09.2018 ** Reklamationen bitte binnen 2 Monaten\"\";\"\"203201809302AB1-P32202569964\"\";\"\"\"\";\"\"30.09.2018\"\"\"\n" +
                "\"01.10.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-50,00\"\";\"\"EUR\"\";\"\"AUTOMAT 00041283 K1 30.09. 16:43\"\";\"\"203201809302ALB-00G6XTKYGVL4\"\";\"\"\"\";\"\"30.09.2018\"\"\"\n" +
                "\"01.10.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-50,00\"\";\"\"EUR\"\";\"\"AUTOMAT 00041283 K1 30.09. 18:28\"\";\"\"203201809302ALB-00I0GSFLLPMO\"\";\"\"\"\";\"\"30.09.2018\"\"\"\n" +
                "\"04.10.2018;\"\"MASS Response Service GmbH\"\";\"\"AT731200010014260235\"\";\"\"BKAUATWWXXX\"\";\"\"\"\";\"\"\"\";\"\"-14,80\"\";\"\"EUR\"\";\"\"KNr: 218986, BNr: SR-598867/2018 spusu MDID:spusu218986\"\";\"\"1200018100213181748547097373\"\";\"\"\"\";\"\"04.10.2018\"\"\"\n" +
                "\"08.10.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"0,00\"\";\"\"EUR\"\";\"\"Kennzeichnung Mündelgeld gelöscht am 05.10.18\"\";\"\"203201810052AAU-00LRTD5TFA74\"\";\"\"\"\";\"\"08.10.2018\"\"\"\n" +
                "\"09.10.2018;\"\"AMAZON INSTANT VIDEO GERMANY GMBH\"\";\"\"DE07300308800013011001\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-1,99\"\";\"\"EUR\"\";\"\"D01-5070720-8971866 Prime Video 158 AMAZON INSTANT VIDEO GERMANY GMBH MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 1693434744406\"\";\"\"201001810052AEI-02XBHA001838\"\";\"\"\"\";\"\"09.10.2018\"\"\"\n" +
                "\"09.10.2018;\"\"Adyen B.V.\"\";\"\"DE25700111105100580006\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"-11,99\"\";\"\"EUR\"\";\"\"1715390901193151 UdemyEU 23931-2670 Adyen B.V. 82-5BBCA6C7-14F3\"\";\"\"203201810092CDC-00ESYJ5DS5SG\"\";\"\"\"\";\"\"09.10.2018\"\"\"\n" +
                "\"16.10.2018;\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"\"\";\"\"30,00\"\";\"\"EUR\"\";\"\"SB-Eigenerlag K1 S72182 16.10/15:35\"\";\"\"203201810162ALH-72182P352401\"\";\"\"\"\";\"\"16.10.2018\"\"\"\n" +
                "\"17.10.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"124,92\"\";\"\"EUR\"\";\"\"305-7355111-7118756 AMZ.Amazon.de 3 AMAZON EU S.A R.L., NIEDERLASSUNG D 3898088450863977 898088450863977\"\";\"\"201001810172AEI-47X4PJ008385\"\";\"\"\"\";\"\"17.10.2018\"\"\"\n" +
                "\"17.10.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-124,92\"\";\"\"EUR\"\";\"\"305-7355111-7118756 Amazon.de 75575 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 38564776768\"\";\"\"201001810162AEI-05X239004051\"\";\"\"\"\";\"\"17.10.2018\"\"\"\n" +
                "\"18.10.2018;\"\"AMAZON EU S.A R.L., NIEDERLASSUNG DEUTSCHLAND\"\";\"\"DE93300308800013441006\"\";\"\"TUBDDEDDXXX\"\";\"\"\"\";\"\"\"\";\"\"-87,64\"\";\"\"EUR\"\";\"\"305-3923371-4038719 Amazon.de 91768 AMAZON EU S.A R.L., NIEDERLASSUNG D MDID:rQ6NMpLD(DUDcetr)cz+6Ih4dWP?HV 72100796765\"\";\"\"201001810172AEI-03X5JQ000788\"\";\"\"\"\";\"\"18.10.2018\"\"\"\n";


        String[] lines = fileContent.split("\n");
        String [] lineElements;
        for(int i = 1; i < lines.length; i++){ //1. zeile überspringen
            lineElements = lines[i].split(";");
            Zahlung z = new Zahlung(lineElements[0], lineElements[1], lineElements[2], lineElements[3], lineElements[4], lineElements[5], lineElements[6], lineElements[7], lineElements[8], lineElements[9], lineElements[10], lineElements[11]);
            z = entfernenVonHochkommasBeiZahlung(z, "\"", ""); // "18.10.2018" --> 18.10.018 ... "100,00" --> 100,00
            zahlungen.add(z);
        }

        Database.initJdbc();
        for (Zahlung z : zahlungen){
            Database.insertIntoDatabase(z); //daten in der db persistieren
        }


        for(Zahlung z : zahlungen){
            System.out.println(z.toString()); //ausgabe der vitalen informationen einer zahlung
        }

        //Database.teardownJdbc();
    }

    private static Zahlung entfernenVonHochkommasBeiZahlung(Zahlung z, String regex, String replacement) {
        z.setBuchungsdatum(z.getBuchungsdatum().replaceAll(regex, replacement));
        z.setBetrag(z.getBetrag().replaceAll(regex, replacement));
        z.setBuchungstext(z.getBuchungstext().replaceAll(regex, replacement));
        z.setErsterfassungsreferenz(z.getErsterfassungsreferenz().replaceAll(regex, replacement));
        z.setNotiz(z.getNotiz().replaceAll(regex, replacement));
        z.setPartner_bankcode(z.getPartner_bankcode().replaceAll(regex, replacement));
        z.setPartner_bic(z.getPartner_bic().replaceAll(regex, replacement));
        z.setPartner_iban(z.getPartner_iban().replaceAll(regex, replacement));
        z.setPartner_kontonummer(z.getPartner_kontonummer().replaceAll(regex, replacement));
        z.setPartner_name(z.getPartner_name().replaceAll(regex, replacement));
        z.setValutadatum(z.getValutadatum().replaceAll(regex, replacement));
        z.setWährung(z.getWährung().replaceAll(regex, replacement));
        return z;
    }

    // momentan NICHT in verwendung [löschen wenn keine verwendung vorhanden ist im späteren verlauf]
    private static String[] replaceCharacterFromArray(String[] lineElements, String regex, String newReplacement) {
        int len = lineElements.length;
        for (int i = 0; i < len; i++){
            lineElements[i] = lineElements[i].replaceAll(regex, newReplacement);
        }
        return lineElements;
    }
}
