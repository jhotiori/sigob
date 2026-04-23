--Valida INSERT de clientes

CREATE OR REPLACE FUNCTION fn_trigger_valida_insert_cliente()
RETURNS TRIGGER AS $$
BEGIN
    IF TRIM(NEW.nmCliente) = '' THEN
        RAISE EXCEPTION 'nmCliente não pode ser vazio.';
    END IF;


    IF TRIM(NEW.nrDocumento) = '' THEN
        RAISE EXCEPTION 'nrDocumento não pode ser vazio.';
    END IF;

    RAISE NOTICE 'Cliente "%" validado com sucesso.', NEW.nmCliente;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_valida_insert_cliente
BEFORE INSERT ON clientes
FOR EACH ROW
EXECUTE FUNCTION fn_trigger_valida_insert_cliente();


--Bloqueia DELETE nos acessos vinculados

CREATE OR REPLACE FUNCTION fn_trigger_bloqueia_delete_acesso()
RETURNS TRIGGER AS $$
DECLARE
    vTotal BIGINT;
BEGIN
    SELECT COUNT(*)
    INTO vTotal
    FROM funcionarios
    WHERE fk_idAcesso = OLD.idAcesso;

    IF vTotal > 0 THEN
        RAISE EXCEPTION
            'Acesso "%" (ID %) não pode ser removido — está vinculado a % funcionário(s).',
            OLD.cdAcesso, OLD.idAcesso, vTotal;
    END IF;

    RAISE NOTICE 'Acesso "%" removido com segurança (sem vínculos).', OLD.cdAcesso;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_bloqueia_delete_acesso
BEFORE DELETE ON acessos
FOR EACH ROW
EXECUTE FUNCTION fn_trigger_bloqueia_delete_acesso();

--Valida UPDATE em produtos

CREATE OR REPLACE FUNCTION fn_trigger_valida_update_produto()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.vlProduto IS NOT NULL AND NEW.vlProduto <= 0 THEN
        RAISE EXCEPTION 'vlProduto deve ser maior que zero. Recebido: %', NEW.vlProduto;
    END IF;

    IF NEW.vlCusto IS NOT NULL AND NEW.vlCusto < 0 THEN
        RAISE EXCEPTION 'vlCusto não pode ser negativo. Recebido: %', NEW.vlCusto;
    END IF;

    IF NEW.vlProduto IS NOT NULL AND NEW.vlCusto IS NOT NULL
       AND NEW.vlProduto < NEW.vlCusto THEN
        RAISE EXCEPTION
            'vlProduto (%) não pode ser menor que vlCusto (%) — margem negativa não permitida.',
            NEW.vlProduto, NEW.vlCusto;
    END IF;

    IF TRIM(NEW.nmProduto) = '' THEN
        RAISE EXCEPTION 'nmProduto não pode ser vazio.';
    END IF;

    IF OLD.vlProduto IS DISTINCT FROM NEW.vlProduto THEN
        RAISE NOTICE 'Produto ID % — vlProduto alterado: % → %', NEW.idProduto, OLD.vlProduto, NEW.vlProduto;
    END IF;

    IF OLD.vlCusto IS DISTINCT FROM NEW.vlCusto THEN
        RAISE NOTICE 'Produto ID % — vlCusto alterado: % → %', NEW.idProduto, OLD.vlCusto, NEW.vlCusto;
    END IF;

    IF OLD.nmProduto IS DISTINCT FROM NEW.nmProduto THEN
        RAISE NOTICE 'Produto ID % — nmProduto alterado: "%" → "%"', NEW.idProduto, OLD.nmProduto, NEW.nmProduto;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_valida_update_produto
BEFORE UPDATE ON produtos
FOR EACH ROW
EXECUTE FUNCTION fn_trigger_valida_update_produto();
