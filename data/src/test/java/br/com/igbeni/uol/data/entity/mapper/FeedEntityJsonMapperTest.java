package br.com.igbeni.uol.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.FeedItemEntity;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FeedEntityJsonMapperTest {

    private static final String JSON_RESPONSE_FEED = "{\n" +
            "\t\"feed\" : [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Mourinho teme futuras compras do City: 'Se isso ocorrer, será difícil'\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/2b/2018/03/13/jose-mourinho-tecnico-do-manchester-united-na-partida-contra-o-sevilla-pela-liga-dos-campeoes-1520991398221_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423110327,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/mourinho-teme-futuras-compras-do-city-se-isso-ocorrer-sera-dificil.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/mourinho-teme-futuras-compras-do-city-se-isso-ocorrer-sera-dificil.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Roma fecha com companhia aérea e terá maior patrocínio da sua história\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/6f/2018/04/23/jogadores-da-roma-exibem-camisa-com-patrocinio-da-qatar-airways-1524491712792_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423105615,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/roma-fecha-com-companhia-aerea-e-tera-maior-patrocinio-da-sua-historia.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/roma-fecha-com-companhia-aerea-e-tera-maior-patrocinio-da-sua-historia.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Luan cita falta de procura, mas Atlético já tem data para discutir contrato\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/9e/2018/04/23/atacante-luan-do-atletico-mg-1524490891655_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423104543,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/luan-cita-falta-de-procura-mas-atletico-ja-tem-data-para-discutir-contrato.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/luan-cita-falta-de-procura-mas-atletico-ja-tem-data-para-discutir-contrato.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Brasil conquista pela sétima vez a Copa América Feminina\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\"updated\" : 20180423101705,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/brasil-conquista-pela-setima-vez-a-copa-america-feminina.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/brasil-conquista-pela-setima-vez-a-copa-america-feminina.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Após título inglês, Guardiola afirma que está atrás de recordes com o City\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\"updated\" : 20180423095559,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/apos-titulo-ingles-guardiola-afirma-que-esta-atras-de-recordes-com-o-city.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/apos-titulo-ingles-guardiola-afirma-que-esta-atras-de-recordes-com-o-city.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Com mais de R$ 500 mi/ano, Messi é o mais bem pago do mundo, diz revista\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/26/2018/04/14/messi-tenta-escapar-da-marcacao-de-parejo-1523724734353_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423092649,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/com-mais-r-400-mi-por-ano-messi-e-o-mais-bem-pago-do-mundo-diz-revista.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/com-mais-r-400-mi-por-ano-messi-e-o-mais-bem-pago-do-mundo-diz-revista.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"AEK volta a ser campeão do Campeonato Grego após 24 anos\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\"updated\" : 20180423091248,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/aek-volta-a-ser-campeao-do-campeonato-grego-apos-24-anos.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/aek-volta-a-ser-campeao-do-campeonato-grego-apos-24-anos.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Jornal: Torcedores do Real gritam para Florentino: 'compra o Neymar'\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/91/2017/06/03/florentino-perez-acena-antes-de-final-da-liga-dos-campeoes-entre-real-madrid-e-juventus-1496529454881_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423090604,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/jornal-torcedores-do-real-gritam-para-florentino-compra-o-neymar.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/2018/04/23/jornal-torcedores-do-real-gritam-para-florentino-compra-o-neymar.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Evolução de Lucas Lima é o ponto alto de um Palmeiras que pode mais\",\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\"updated\" : 20180423080000,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/evolucao-de-lucas-lima-e-o-ponto-alto-de-um-palmeiras-que-pode-mais.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/evolucao-de-lucas-lima-e-o-ponto-alto-de-um-palmeiras-que-pode-mais.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t},\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"type\" : \"news\",\n" +
            "\t\t\t\"title\" : \"Bola aérea e gols perdidos ligam alerta do Vasco para a Libertadores\",\n" +
            "\t\t\t\t\t\t\t\"thumb\" : \"https://imguol.com/c/esporte/e5/2018/04/22/wellington-paulista-observa-henrique-em-lance-de-chapecoense-x-vasco-1524426411093_142x100.jpg\",\t\t\t\t\t\t\"updated\" : 20180423080000,\n" +
            "\t\t\t\"share-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/bola-aerea-e-gols-perdidos-ligam-alerta-do-vasco-para-a-libertadores.htm\",\n" +
            "\t\t\t\"webview-url\" : \"https://esporte.uol.com.br/futebol/ultimas-noticias/lancepress/2018/04/23/bola-aerea-e-gols-perdidos-ligam-alerta-do-vasco-para-a-libertadores.htm?app=uol-placar-futebol&plataforma=iphone&template=v2\"\n" +
            "\t\t}]\n" +
            "}";

    private FeedEntityJsonMapper feedEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        feedEntityJsonMapper = new FeedEntityJsonMapper();
    }

    @Test
    public void testTransformFeedEntityHappyCase() {
        FeedEntity feedEntity = feedEntityJsonMapper.transformFromEntity(JSON_RESPONSE_FEED);

        assertThat(feedEntity, is(instanceOf(FeedEntity.class)));

        for (FeedItemEntity feedItemEntity : feedEntity.getFeedItemEntities()) {
            assertThat(feedItemEntity, is(instanceOf(FeedItemEntity.class)));
        }

        assertThat(feedEntity.getFeedItemEntities().size(), is(10));
    }

    @Test
    public void testTransformFeedEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        feedEntityJsonMapper.transformFromEntity("feed");
    }
}