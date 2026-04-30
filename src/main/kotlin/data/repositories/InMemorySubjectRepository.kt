package ru.mirea.shylit.studydeadline.data.repositories

import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository

class InMemorySubjectRepository : SubjectRepository {

    private val subjects = listOf(
        Subject(
            id = 1,
            name = "Разработка клиент-серверных мобильных приложений",
            description = "Дисциплина по разработке мобильных приложений с серверной частью"
        ),
        Subject(
            id = 2,
            name = "Мобильная разработка",
            description = "Дисциплина по разработке Android-приложений"
        ),
        Subject(
            id = 3,
            name = "Проектирование баз данных",
            description = "Дисциплина по проектированию логических и физических моделей данных"
        )
    )

    override fun getAllSubjects(): List<Subject> = subjects
}