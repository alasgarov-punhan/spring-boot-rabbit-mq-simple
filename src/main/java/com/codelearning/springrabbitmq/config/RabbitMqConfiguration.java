package com.codelearning.springrabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 * BU SPRING tərəfindəki Configuration bunun içərisində bir neçə obyektlər (instance lər) yaradacağıq
 * O instance ilə bizim Queue lə danışan componentlərimiz olacaq yoxsa default olaraq danlşdıra bilmərik
 * DB connection olanda statementlər filan  yaradırqdıq göndərirdik eyni məntiqdir.
 * Bu Bir Spring Configurationdirsa bunun @COnfiguration annotation lazımdır ki Spring bunu initialize etsin SPRİNG Configuration u olduğunu anlasın
 *
 * 1 exchange nin direct oldugunu demek lazimdir
 * 2 queue ni demeyimiz lazimdir adini filan
 * 3 bir routing yada bind key eyni seydir
 * 4 queue yaratmagimiz lazimdir import edende baxmaq lazimdir amqp den gelecek
 * */
@EnableRabbit
@Configuration
public class RabbitMqConfiguration {

    @Value("${sr.rabbit.queue.name}")
    private String queueName;

    @Value("${sr.rabbit.routing.name}")
    private String routingName;

    @Value("${sr.rabbit.exchange.name}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // ioc container icine bir obyekt qoyum o obyekti her yerede istifade edim demek ucun bele bir queue yaradiriq
    // import edende baxiriq parametr gondermekle eger client connect deyilse mesaj silinsinmi queue ni sisirtmesin
    // mesaj butun consumer lere getsin ya yox muxtelif deyereler var queue nin uzerine click edib documentataiona  baxmaq lazimdir durabled,exclusive ,autodelete
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    //exchange ni yaradiriq buda bir spring instance olacaq deye bean add edirik
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    // imdi exchange ve queue var bunlari birbiriyle elaqelendirmeyimiz lazimdir
    // queue ve exchange yaradildiq bunlar dependency injection container icerisinde var mene verilsin demeyimiz lazimdri springe
    // spring bu classi yaradanda constructor injection edecek burda o
    // return de deyirik queue ni gotur exchange mize bind ele
    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingName);
    }
}

//Spring bir cox jar lari configuration lari gorduyu ucun spring bizim ucun
//ioc container icinde Rabbit Template detilen bir sey var bizim rabbit queue sine gedib gelmek ucun yegeane hisse bu olacaq