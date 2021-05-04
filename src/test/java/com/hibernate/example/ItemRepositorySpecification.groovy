package com.hibernate.example

import com.hibernate.example.model.Item
import com.hibernate.example.model.MonetaryAmount
import com.hibernate.example.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT /*To enable h2 console*/)
@TestPropertySource(locations = "classpath:test.properties")
class ItemRepositorySpecification extends Specification {

    private static final String ITEM_NAME = "NAME"

    @Autowired
    private ItemRepository itemRepository

    def "should persist item entity"() {
        given:
        def item = getItem()

        when:
        def id = itemRepository.save(item)
        def itemFromDb = itemRepository.getById(id)
        def itemFromDbSql = itemRepository.getByIdSql(id)
        def itemFromDbCriteria = itemRepository.getByIdCriteriaQuery(id)

        then:
        itemFromDb.getName() == ITEM_NAME
        itemFromDbSql.getName() == ITEM_NAME
        itemFromDbCriteria.getName() == ITEM_NAME
    }


    def "should update item name"() {
        given:
        def item = getItem()

        when:
        def id = itemRepository.save(item)

        item.setName("TestTest")
        itemRepository.update(item)

        def retrieved = itemRepository.getById(id)

        then:
        true
        retrieved.getName() != ITEM_NAME
    }

    def "should retrieve short description"() {
        given:
        def item = getItem()

        when:
        def id = itemRepository.save(item)
        def retrieved = itemRepository.getById(id)

        then:
        retrieved.getShortDescription() == "descriptio..."
    }

    def "should correctly use monetary amount type adapter"() {
        given:
        def item = getItem()
        item.setBuyNowPrice(MonetaryAmount.fromString("30.23 USD"))

        when:
        def id = itemRepository.save(item)
        def retrieved = itemRepository.getById(id)

        then:
        retrieved.getBuyNowPrice().toString() == "30.23 USD"
    }

    private static def getItem() {
        new Item(ITEM_NAME, "description description description description", BigDecimal.TEN)
    }
}
