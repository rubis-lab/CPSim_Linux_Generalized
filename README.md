# CPSim Linux Generalized 
### Last updated 2023-01-13 
### Author Seonghyeon Park

## The CPSim is open source project. it stands for Cyber Physical Simulator.

   
   
    // SHARED OBJECT VARIABLE SHARING TEST
    // NAME OF SHARED VARIABLES: shared1, shared2, shared3, shared4
    // dlerror();
    // SO FILE MADE WITH:
    // gcc -std=c++17 -shared -o example.so -fPIC example.cpp
    // SYMBOL CHECKING DONE WITH
    // nm -D example.so
    // Job ex1;
    // ex1.loadFunction(utils::cpsim_path + "/sharedObjectFiles/example.so", "sim_main");
    // ex1.run();
    // std::cout << "(inside main) shared values are: " << shared::shared1 << " " << shared::shared2 << " " << shared::shared3 << " " << shared::shared4 << std::endl;
    // shared::shared1 = 20;
    // shared::shared2 = 30;
    // shared::shared3 = 40;
    // shared::shared4 = 50;
    // ex1.run();
    // std::cout << "(inside main) shared values are now: " << shared::shared1 << " " << shared::shared2 << " " << shared::shared3 << " " << shared::shared4 << std::endl;
    // std::cout << "(inside main) shared variable is: " << *ex1.shared_variable << std::endl;
    // *ex1.shared_variable = 15;
    // return 0;
    // ENDS HERE
