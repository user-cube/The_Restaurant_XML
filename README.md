# The Restaurant XML Version

Problem of "The Restaurant" with XML message exchange.

## Problem Statement
Events portray the activities that take place when a group of students, enrolled in Computação Distri-
buída, go to a famous restaurant downtown for a gourmet dinner to celebrate the beginning of the second
semester. There are three main locations within the restaurant that should be accounted for: the table
where the students sit to have their meal, the kitchen where the chef prepares it according to the orders
placed by the students, and the bar where the waiter stands waiting for service requests. There are,
furthermore, three kinds of interacting entities: N students , one waiter and one chef.
The activities are organized as described below
― the students arrive one by one at random times and sit at the table, chatting with one another while
waiting for the group to be complete;
― once a students sits, the waiter brings her/him a copy of the menu so that she/he can select among
the offered dishes the ones of her/his preference;
― the first student to arrive gathers the individual plate choices of her/his companions and prepares
the order for the whole group;
― once the order has been completed, she/he calls the waiter and informs him about its content;
― the waiter, then, goes to the kitchen and places the order to the chef;
― the order consists of M courses per participant in the dinner;
― the waiter serves them in succession, only passing to the next course when signaled by the last
student to finish eating that everybody is ready;
― in the end, the student that was the last to arrive signals the waiter to bring her/him the bill after
both her/his companions and her/himself have finished the dessert and pays it in full as a form of
penalty for being late;
― all students leave together the restaurant and go home to study because an assignment deadline is
soon due.
Assume there are seven students and that the order consists of three courses per participant in the
dinner: a starter, a main course and a dessert. Write a simulation of the life cycle of the chef, the waiter
and the students using one of the models for thread communication and synchronization which have been
studied: monitors or semaphores and shared memory.
One aims for a distributed solution with multiple information sharing regions, written in Java, run in
Linux and which terminates. A logging file that describes the evolution of the internal state of the
problem in a clear and precise way, must be included.

## Workflow
![Chef and Waiter Life cycle](https://image.ibb.co/b73Wvf/workflow1.png)
![Client Life cycle](https://image.ibb.co/d1FN1L/workflow2.png)

