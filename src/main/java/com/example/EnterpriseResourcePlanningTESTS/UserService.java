package com.example.EnterpriseResourcePlanningTESTS;

//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public List<User> listAll(){
//        return (List<User>) userRepository.findAll();
//    }
//
//    public List<User> listAll(String keyword){  /////////////
//        if(keyword != null){
//            return userRepository.findAll(keyword);
//        }
//        return (List<User>) userRepository.findAll();
//    }
//
//
//
//
//    public void save(User user) {
//        userRepository.save(user);
//    }
//
//    public User get(Long id) throws CustomerNotFoundException {
//        Optional<User> result = userRepository.findById(id);
//        if(result.isPresent()){
//            return result.get();
//        }
//        throw new CustomerNotFoundException("Could not find any Users with Id: " + id);
//    }
//
//    public void delete(Long id) throws CustomerNotFoundException {
//        Long count = userRepository.countById(id);
//        if (count == null || count == 0){
//            throw new CustomerNotFoundException("Could not find any Users with Id: " + id);
//        }
//        userRepository.deleteById(id);
//    }
//
//
//    public void addAllUsers(Model model) {
//        Iterable<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
//    }
//}
