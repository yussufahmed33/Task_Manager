package com.example.Task.Maneger.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.model.roles;
import com.example.Task.Maneger.repository.UsersRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserDetailesService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserDetailesServiceDiffblueTest {
    @Autowired
    private UserDetailesService userDetailesService;

    @MockBean
    private UsersRepository usersRepository;

    /**
     * Method under test: {@link UserDetailesService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setEmail("jane.doe@example.org");
        userModel.setFirstName("Jane");
        userModel.setId(1L);
        userModel.setLastName("Doe");
        userModel.setPassword("iloveyou");
        userModel.setRoles(new HashSet<>());
        userModel.setTasks(new HashSet<>());
        userModel.setUsername("janedoe");
        when(usersRepository.findByUsername(Mockito.<String>any())).thenReturn(userModel);

        // Act
        UserDetails actualLoadUserByUsernameResult = userDetailesService.loadUserByUsername("janedoe");

        // Assert
        verify(usersRepository).findByUsername(eq("janedoe"));
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertTrue(authorities instanceof Set);
        assertTrue(actualLoadUserByUsernameResult instanceof User);
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(authorities.isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }

    /**
     * Method under test: {@link UserDetailesService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        roles roles = new roles();
        roles.setId(1L);
        roles.setRoleName("Role Name");

        HashSet<roles> rolesSet = new HashSet<>();
        rolesSet.add(roles);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getPassword()).thenReturn("iloveyou");
        when(userModel.getUsername()).thenReturn("janedoe");
        when(userModel.getRoles()).thenReturn(rolesSet);
        doNothing().when(userModel).setEmail(Mockito.<String>any());
        doNothing().when(userModel).setFirstName(Mockito.<String>any());
        doNothing().when(userModel).setId(anyLong());
        doNothing().when(userModel).setLastName(Mockito.<String>any());
        doNothing().when(userModel).setPassword(Mockito.<String>any());
        doNothing().when(userModel).setRoles(Mockito.<Set<roles>>any());
        doNothing().when(userModel).setTasks(Mockito.<Set<TaskModel>>any());
        doNothing().when(userModel).setUsername(Mockito.<String>any());
        userModel.setEmail("jane.doe@example.org");
        userModel.setFirstName("Jane");
        userModel.setId(1L);
        userModel.setLastName("Doe");
        userModel.setPassword("iloveyou");
        userModel.setRoles(new HashSet<>());
        userModel.setTasks(new HashSet<>());
        userModel.setUsername("janedoe");
        when(usersRepository.findByUsername(Mockito.<String>any())).thenReturn(userModel);

        // Act
        UserDetails actualLoadUserByUsernameResult = userDetailesService.loadUserByUsername("janedoe");

        // Assert
        verify(userModel).getPassword();
        verify(userModel).getRoles();
        verify(userModel).getUsername();
        verify(userModel).setEmail(eq("jane.doe@example.org"));
        verify(userModel).setFirstName(eq("Jane"));
        verify(userModel).setId(eq(1L));
        verify(userModel).setLastName(eq("Doe"));
        verify(userModel).setPassword(eq("iloveyou"));
        verify(userModel).setRoles(isA(Set.class));
        verify(userModel).setTasks(isA(Set.class));
        verify(userModel).setUsername(eq("janedoe"));
        verify(usersRepository).findByUsername(eq("janedoe"));
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities instanceof Set);
        assertTrue(actualLoadUserByUsernameResult instanceof User);
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }

    /**
     * Method under test: {@link UserDetailesService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        // Arrange
        roles roles = new roles();
        roles.setId(1L);
        roles.setRoleName("Role Name");

        roles roles2 = new roles();
        roles2.setId(2L);
        roles2.setRoleName("com.example.Task.Maneger.model.roles");

        HashSet<roles> rolesSet = new HashSet<>();
        rolesSet.add(roles2);
        rolesSet.add(roles);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getPassword()).thenReturn("iloveyou");
        when(userModel.getUsername()).thenReturn("janedoe");
        when(userModel.getRoles()).thenReturn(rolesSet);
        doNothing().when(userModel).setEmail(Mockito.<String>any());
        doNothing().when(userModel).setFirstName(Mockito.<String>any());
        doNothing().when(userModel).setId(anyLong());
        doNothing().when(userModel).setLastName(Mockito.<String>any());
        doNothing().when(userModel).setPassword(Mockito.<String>any());
        doNothing().when(userModel).setRoles(Mockito.<Set<roles>>any());
        doNothing().when(userModel).setTasks(Mockito.<Set<TaskModel>>any());
        doNothing().when(userModel).setUsername(Mockito.<String>any());
        userModel.setEmail("jane.doe@example.org");
        userModel.setFirstName("Jane");
        userModel.setId(1L);
        userModel.setLastName("Doe");
        userModel.setPassword("iloveyou");
        userModel.setRoles(new HashSet<>());
        userModel.setTasks(new HashSet<>());
        userModel.setUsername("janedoe");
        when(usersRepository.findByUsername(Mockito.<String>any())).thenReturn(userModel);

        // Act
        UserDetails actualLoadUserByUsernameResult = userDetailesService.loadUserByUsername("janedoe");

        // Assert
        verify(userModel).getPassword();
        verify(userModel).getRoles();
        verify(userModel).getUsername();
        verify(userModel).setEmail(eq("jane.doe@example.org"));
        verify(userModel).setFirstName(eq("Jane"));
        verify(userModel).setId(eq(1L));
        verify(userModel).setLastName(eq("Doe"));
        verify(userModel).setPassword(eq("iloveyou"));
        verify(userModel).setRoles(isA(Set.class));
        verify(userModel).setTasks(isA(Set.class));
        verify(userModel).setUsername(eq("janedoe"));
        verify(usersRepository).findByUsername(eq("janedoe"));
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities instanceof Set);
        assertTrue(actualLoadUserByUsernameResult instanceof User);
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }
}
