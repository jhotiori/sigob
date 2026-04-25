ALTER TABLE acessos
    ADD COLUMN codigo VARCHAR(16);

ALTER TABLE categorias
    ADD COLUMN codigo VARCHAR(16);

ALTER TABLE produtos
    ALTER COLUMN moeda_id DROP NOT NULL;