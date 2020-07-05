
INSERT INTO building (address, owner, size, market_value, property_type) VALUES
  ('Algirdo 41-a, Vilnius', 'Lina',   '1000',  '1000',  'apartment'),
  ('Algirdo 43-a, Vilnius', 'Rokas',   '2000',  '2000',  'house'),
  ('Algirdo 46-a, Vilnius', 'Ula',   '3000',  '3111',  'industrial'),
 ('Vilniaus 46-a, Vilnius', 'Lina',   '300000',  '300',  'building'),
  ('Ramuniu 47-a, Vilnius', 'Milda',   '5000', '5000',  'apartment'),
  ('Pievu 8-2, Vilnius', 'Ula',   '10',  '1000',  'building');

INSERT INTO tax (property_type, tax_rate) VALUES
  ('apartment', '0.221'),
  ('house', '0.001'),
  ('industrial', '0.3');