CREATE OR REPLACE FUNCTION fn_trigger_valida_produto()
RETURNS TRIGGER AS $$
BEGIN

    IF TRIM(NEW.codigo) = '' THEN
        RAISE EXCEPTION 'codigo do produto não pode ser vazio.';
END IF;

    IF TRIM(NEW.nome) = '' THEN
        RAISE EXCEPTION 'nome do produto não pode ser vazio.';
END IF;

    IF NEW.valor_compra IS NULL OR NEW.valor_compra <= 0 THEN
        RAISE EXCEPTION 'valor_compra deve ser maior que zero. Recebido: %', NEW.valor_compra;
END IF;

    IF NEW.valor_venda IS NULL OR NEW.valor_venda <= 0 THEN
        RAISE EXCEPTION 'valor_venda deve ser maior que zero. Recebido: %', NEW.valor_venda;
END IF;

    IF NEW.valor_venda <= NEW.valor_compra THEN
        RAISE EXCEPTION 'valor_venda (%) não pode ser menor ou igual a valor_compra (%) — margem negativa não permitida.',
            NEW.valor_venda, NEW.valor_compra;
END IF;

    IF TG_OP = 'UPDATE' THEN
        IF OLD.nome IS DISTINCT FROM NEW.nome THEN
            RAISE NOTICE 'Produto ID % — nome alterado: "%" → "%"', NEW.id, OLD.nome, NEW.nome;
END IF;

        IF OLD.valor_compra IS DISTINCT FROM NEW.valor_compra THEN
            RAISE NOTICE 'Produto ID % — valor_compra alterado: % → %', NEW.id, OLD.valor_compra, NEW.valor_compra;
END IF;

        IF OLD.valor_venda IS DISTINCT FROM NEW.valor_venda THEN
            RAISE NOTICE 'Produto ID % — valor_venda alterado: % → %', NEW.id, OLD.valor_venda, NEW.valor_venda;
END IF;
END IF;

    RAISE NOTICE 'Produto "%" (%) validado com sucesso.', NEW.nome, TG_OP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_valida_produto
    BEFORE INSERT OR UPDATE ON produtos
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_trigger_valida_produto();


CREATE OR REPLACE FUNCTION fn_trigger_bloqueia_delete_categoria()
RETURNS TRIGGER AS $$
DECLARE
vTotal BIGINT;
BEGIN
SELECT COUNT(*)
INTO vTotal
FROM produtos
WHERE categoria_id = OLD.id;

IF vTotal > 0 THEN
        RAISE EXCEPTION
            'Categoria "%" (ID %) não pode ser removida — está vinculada a % produto(s).',
            OLD.nome, OLD.id, vTotal;
END IF;

    RAISE NOTICE 'Categoria "%" removida com segurança (sem vínculos).', OLD.nome;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_bloqueia_delete_categoria
    BEFORE DELETE ON categorias
    FOR EACH ROW
    EXECUTE FUNCTION fn_trigger_bloqueia_delete_categoria();


CREATE OR REPLACE FUNCTION fn_trigger_valida_venda()
RETURNS TRIGGER AS $$
BEGIN

    IF NEW.status NOT IN ('aberta', 'finalizada') THEN
        RAISE EXCEPTION
            'status inválido: "%". Valores aceitos: "aberta" ou "finalizada".',
            NEW.status;
END IF;

    IF NEW.status = 'finalizada' AND NEW.data_finalizada IS NULL THEN
        NEW.data_finalizada := NOW();
        RAISE NOTICE 'Venda ID % finalizada — data_finalizada preenchida automaticamente: %',
            NEW.id, NEW.data_finalizada;
END IF;

    IF TG_OP = 'UPDATE'
       AND OLD.status = 'finalizada'
       AND NEW.status = 'aberta' THEN
        RAISE EXCEPTION
            'Venda ID % já está finalizada e não pode ser reaberta.', OLD.id;
END IF;

    RAISE NOTICE 'Venda ID % validada com sucesso. Status: %.', NEW.id, NEW.status;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_valida_venda
    BEFORE INSERT OR UPDATE ON vendas
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_trigger_valida_venda();