package com.tg;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {
            // start a transaction
            session.beginTransaction();

            // get instructor by id
            int theId = 10;
            Instructor tempInstructor = session.get(Instructor.class, theId);
            System.out.println("Found instructor: " + tempInstructor);

            // delete the intructor
            if (tempInstructor != null) {
                System.out.println("Deleting: " + tempInstructor);
                // Note: will ALSO delete associated "details" object because of CascadeType.ALL
                session.delete(tempInstructor);
            }

            // commit transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        } finally {
            factory.close();
        }
    }

}