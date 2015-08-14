Welcome to Fluxo de Caixa
==

Whats is this?
--

Fluxo de Caixa (Cash Flow) is a command line personal cash flow software. Inspired by Linux style commands, its simple, easy and versatile.

Command Sintax
--

*   conta add <nome> [contabilizavel]

    Adds a new conta (account) with name **nome** object to database. **contabilizavel** mean this conta have its balance calculated in lists.

*   conta ls

    Lists the conta objects

*   conta rm <nome>

    Remove the conta object with name **nome**.

*   lanc add <nome conta origem> <nome conta destino> <valor> [observacao] [[data] | [--trans-id=transacao id]]

*   lanc ls [--trans-id=<id da transacao>] [--origem=<nome da origem>] [--destino=<nome do destino>] [--valor=<valor>] [--observacao=<regex>]

*   lanc ch <lancamento id> [--trans-id=<id da transacao>] [--origem=<nome da origem>] [--destino=<nome do destino>] [--valor=<valor>] [--observacao=<observacao>]

*   lanc rm <lancamento id>

*   trans add <data> [observacao]

*   trans ls [--posteriores-a=<data>] [--anteriores-a=<data>]

*   trans ch <id> [--data=nova data] [-observacao=observacao]

*   trans rm <id>

*   saldo [ --contas=<nomes das contas separados por ,> ] [ --por-lancamento | --diario | --semanal | --mensal | --atual ]
