# Comandos e Permissões

Aqui você encontra a lista de comandos disponíveis e as permissões necessárias.

## Comandos

| Comando | Aliases | Descrição | Permissão |
| :--- | :--- | :--- | :--- |
| `/atmosfera` | `/rtw`, `/atm` | Mostra o painel de status com informações de depuração (hora real, clima atual, conexão com API). | `atmosfera.status` |
| `/atmosfera reload` | *Nenhum* | Recarrega as configurações do `config.yml`. *(Em breve)* | `atmosfera.admin` |

## Permissões

### `atmosfera.status`
*   **Padrão**: Apenas OPs.
*   **Descrição**: Permite ver se o plugin está sincronizando corretamente e ver os dados brutos da API.

### `atmosfera.admin`
*   **Padrão**: Apenas OPs.
*   **Descrição**: Permite recarregar o plugin e gerenciar configurações administrativas.

---

## Comandos Bloqueados
Se configurado no `config.yml`, o Atmosfera pode bloquear os seguintes comandos nativos para evitar desincronização:

*   `/time set`
*   `/time add`
*   `/weather rain`
*   `/weather clear`
*   `/toggledownfall`
