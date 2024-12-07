# Atividade_AW_SIGEPP

## Commits

Para facilitar desenvolvimento, use mensagens descritivas nos commits, no inicio da mensagem use commits semanticos segundo o quadro abaixo:

<table>
  <thead>
    <tr>
      <th>Tipo do commit</th>
      <th>Codigo</th>
    </tr>
  </thead>
 <tbody>
    <tr>
      <td>Alterações de revisão de código</td>
      <td>👌 <code>:ok_hand: style</code></td>
    </tr>
    <tr>
      <td>Bugfix</td>
      <td>🐛 <code>:bug: fix:</code></td>
    </tr>
    <tr>
      <td>Configuração</td>
      <td>🔧 <code>:wrench: chore:</code></td>
    </tr>
    <tr>
      <td>Documentação</td>
      <td>📚 <code>:books: docs:</code></td>
    </tr>
    <tr>
      <td>Em progresso</td>
      <td>🚧 <code>:construction:</code></td>
    </tr>
    <tr>
      <td>Estilização de interface</td>
      <td>💄 <code>:lipstick: feat</code></td>
    </tr>
    <tr>
      <td>Mover/Renomear</td>
      <td>🚚 <code>:truck: chore:</code></td>
    </tr>
    <tr>
      <td>Novo recurso</td>
      <td>✨ <code>:sparkles: feat:</code></td>
    </tr>
    <tr>
      <td>Performance</td>
      <td>⚡ <code>:zap: perf:</code></td>
    </tr>
    <tr>
        <td>Refatoração</td>
        <td>♻️ <code>:recycle: refactor:</code></td>
    </tr>
    <tr>
      <td>Removendo um arquivo</td>
      <td>🔥 <code>:fire:</code></td>
    </tr>
    <tr>
      <td>Revertendo mudanças</td>
      <td>💥 <code>:boom: fix:</code></td>
    </tr>
    <tr>
      <td>Tag de versão</td>
      <td>🔖 <code>:bookmark:</code></td>
    </tr>
    <tr>
      <td>Testes</td>
      <td>🧪 <code>:test_tube: test:</code></td>
    </tr>
    <tr>
      <td>Tratamento de erros</td>
      <td>🥅 <code>:goal_net: fix:</code></td>
    </tr>
    <tr>
      <td>Mundança no Makefile e arquvos de build</td>
      <td>🔨 <code>:hammer: build:</code></td>
    </tr>
    <tr>
      <td>Dados</td>
      <td>🗃️ <code>:card_file_box: raw:</code></td>
    </tr>
  </tbody>
</table>

As palavras chaves são as seguintes:
- `feat`- Commits do tipo feat indicam que seu trecho de código está incluindo um **novo recurso** (se relaciona com o MINOR do versionamento semântico).

- `fix` - Commits do tipo fix indicam que seu trecho de código commitado está **solucionando um problema** (bug fix), (se relaciona com o PATCH do versionamento semântico).

- `docs` - Commits do tipo docs indicam que houveram **mudanças na documentação**, como por exemplo no Readme do seu repositório. (Não inclui alterações em código).

- `test` - Commits do tipo test são utilizados quando são realizadas **alterações em testes**, seja criando, alterando ou excluindo testes unitários. (Não inclui alterações em código)

- `build` - Commits do tipo build são utilizados quando são realizadas modificações em **arquivos de build e dependências**.

- `perf` - Commits do tipo perf servem para identificar quaisquer alterações de código que estejam relacionadas a **performance**.

- `style` - Commits do tipo style indicam que houveram alterações referentes a **formatações de código**, semicolons, trailing spaces, lint... (Não inclui alterações em código).

- `refactor` - Commits do tipo refactor referem-se a mudanças devido a **refatorações que não alterem sua funcionalidade**, como por exemplo, uma alteração no formato como é processada determinada parte da tela, mas que manteve a mesma funcionalidade, ou melhorias de performance devido a um code review.

- `chore` - Commits do tipo chore indicam **atualizações de tarefas** de build, configurações de administrador, pacotes... como por exemplo adicionar um pacote no gitignore. (Não inclui alterações em código)

- `raw` - Commits to tipo raw indicam mudanças relacionadas a arquivos de configurações, dados, features, parametros.

Exemplos:
- <code>git commit -m ":books: docs: Atualização do README"</code>
- <code>git commit -m ":construction: trabalhando na classe de interpretador"</code>
- <code>git commit -m ":sparkles: feat: interpretador terminado"</code>

## Inicialização do projeto

<ol>
  <li>Acesse o [Spring Initalizer](https://start.spring.io/);</li>
  <li>Selecione: <b>Maven</b>; <b>Java</b>; Spring Boot <b>3.4.0</b>; Packaging: <b>Jar</b>; Java: <b>17</b>
  <li>Adicione as seguintes dependências: <b>Spring Web</b>, <b>JDBC API</b>, <b>MySQL Driver</b>, <b>Spring Data JPA</b>, <b>Docker Compose Support</b>, <b>Testcontainers</b>.</li>
  <li>Clique <b>Generate</b>.</li>
  <li>Descompacte a pasta gerada e comece a usar!</li>
</ol>