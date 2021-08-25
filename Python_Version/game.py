import pygame
import time

from pygame.locals import*
from time import sleep

class Mario():
    def __init__(self, xPos, yPos):
        #variables to deal with movement
        self.x = xPos
        self.preX = xPos
        self.y = yPos
        self.preY = yPos
        self.vert_vel = 0
        self.toggle_Animate = 0
        self.toggle_Jump = 0
        self.jump_Hight = 0
        self.grounded = 0
        self.atk = 0
        
        #variables to deal with animation
        self.mario_images = []
        self.mario_image1 = pygame.image.load("mario1.png")
        self.mario_image2 = pygame.image.load("mario2.png")
        self.mario_image3 = pygame.image.load("mario3.png")
        self.mario_image4 = pygame.image.load("mario4.png")
        self.mario_image5 = pygame.image.load("mario5.png")
        self.mario_image_num = 0
        self.mario_images.append(self.mario_image1)
        self.mario_images.append(self.mario_image2)
        self.mario_images.append(self.mario_image3)
        self.mario_images.append(self.mario_image4)
        self.mario_images.append(self.mario_image5)
        
        #static variables
        self.width = 60
        self.hight = 96

    #Method to move mario
    def movement(self, direction):
        #Gravity componenet
        self.vert_vel += 1.2
        self.y += self.vert_vel
        #Moving to the right
        if direction == 2:
            self.x += 5
        #Moving to the left
        if direction == 1:
            self.x -= 5
        #Making sure mario isnt sinking into the ground
        if self.y > 450:
            self.vert_vel = 0
            self.y = 450
        #If mario is on the ground toggle grounded to allow a jump
        if self.y == 450:
            self.grounded = 1
            self.vert_vel = 0
        else:
            self.grounded = 0
            self.jump_Hight = 0
            
    #Method to animate mario while he is moving
    def animate(self, toggle):
        if toggle > 0:
            self.mario_image_num = (self.mario_image_num + 1) % 5
    
    #Method to make mario jump when he is on the ground
    def jump(self):
        if self.grounded > 0:
            if self.toggle_Jump > 0:
                self.vert_vel = -15 * (self.jump_Hight * .5)
                self.y -= 1
            else:
                self.vert_vel = 0
                
    #Method to save the past position
    def savePastPos(self):
        self.preX = self.x
        self.preY = self.y
        
    #Method to get out of tubes
    def getOutOfTube(self, tube_x, tube_y):
        if (self.preX <= tube_x):
            self.x = tube_x
        if (self.preX >= tube_x + 110):
            self.x = tube_x + 110
        if (self.preY >= tube_y + 400):
            self.y = tube_y + 400
        if (self.preY <= tube_y):
            self.y = tube_y - self.hight
            self.grounded = 1
            
        

class Tube():
    def __init__(self, xPos, yPos):
        self.width = 110
        self.hight = 400
        self.x = xPos
        self.y = yPos
        self.tube_image = pygame.image.load("Tube.png")
        
    #Method to detect when something is colliding with a tube1
    def tube_Collision(self, user_x, user_y):
        if user_x < self.x:
            return False
        if user_x > self.x + self.width:
            return False
        if user_y + 96 < self.y:
            return False
        if user_y > self.y + self.hight:
            return False
        return True
        
        
class Goomba():
    def __init__(self, xPos, yPos):
        #variables to deal with movement
        self.x = xPos
        self.preX = xPos
        self.y = yPos
        self.preY = yPos
        self.toggle_Fire = 0
        self.frames_On_Fire = 0
        self.direction = 0
        #variables to deal with animation
        self.goomba_images = []
        self.goomba_image1 = pygame.image.load("goomba.png")
        self.goomba_image2 = pygame.image.load("goomba_fire.png")
        self.goomba_images.append(self.goomba_image1)
        self.goomba_images.append(self.goomba_image2)
        self.goomba_image_num = 0
        
    def movement(self):
        if self.direction == 1:
            self.x -= 3
        if self.direction == 0:
            self.x += 3
        else:
            self.x = self.x
        if self.toggle_Fire == 1:
            self.frames_On_Fire += 1
            self.goomba_image_num = 1
    
class Fireball():
    def __init__(self, xPos, yPos):
        self.x = xPos
        self.y = yPos
        self.width = 47
        self.hight = 47
        self.vert_vel = 0
        self.offScreen = 0
        self.used = 0
        self.fireball_image = pygame.image.load("fireball.png")
    
    def movement(self):
        self.vert_vel += 0.2
        self.y += self.vert_vel
        if self.y > 500:
            self.vert_vel -= 1.2
        self.x += 3
        

    def fireball_Collision(self, user_x, user_y):
        if user_x < self.x:
            return False
        if user_x > self.x + self.width:
            return False
        if user_y + 96 < self.y:
            return False
        if user_y > self.y + self.hight:
            return False
        return True

class Model():
    def __init__(self):
        self.direction = 0
        self.goombas_Alive = 1
        self.mario = Mario(0,0)
        self.tube1 = Tube(200, 300)
        self.tube2 = Tube(450, 350)
        self.goomba = Goomba(400, 435)
        self.fireballs = []
        

    def update(self):
        #self.mario.savePastPos()
        self.mario.movement(self.direction)
        if self.goombas_Alive == 1:
            self.goomba.movement()
            if self.tube1.tube_Collision(self.goomba.x, self.goomba.y):
                self.goomba.direction = 0
            if self.tube2.tube_Collision(self.goomba.x, self.goomba.y):
                self.goomba.direction = 1
        self.mario.animate(self.mario.toggle_Animate)
        self.mario.jump()
        if self.tube1.tube_Collision(self.mario.x, self.mario.y):
            self.mario.getOutOfTube(self.tube1.x, self.tube1.y)
        if self.tube2.tube_Collision(self.mario.x, self.mario.y):
            self.mario.getOutOfTube(self.tube2.x, self.tube2.y)
        
        for i in range(len(self.fireballs)):
            self.fireballs[i].movement()
            if self.goombas_Alive == 1:
                if self.fireballs[i].fireball_Collision(self.goomba.x, self.goomba.y):
                    self.goomba.direction = 2
                    self.goomba.toggle_Fire = 1
                    self.fireballs[i].used = 1
                if self.goomba.frames_On_Fire > 25:
                    self.goombas_Alive = 0
            if self.fireballs[i].x > (self.mario.x + 650):
                self.fireballs[i].offScreen = 1
            if self.fireballs[i].offScreen == 1:
                self.fireballs[i].used = 1
    def fireballATK(self):
        self.fireball = Fireball(self.mario.x, self.mario.y)
        self.fireballs.append(self.fireball)
        

class View():
    def __init__(self, model):
        screen_size = (800,600)
        self.screen = pygame.display.set_mode(screen_size, 32)
        self.ground_image = pygame.image.load("ground.png")
        self.model = model

    def update(self):    
        self.screen.fill([0,100,150])
        self.screen.blit(self.model.mario.mario_images[self.model.mario.mario_image_num], ((self.model.mario.x - self.model.mario.x + 150), self.model.mario.y))
        self.screen.blit(self.model.tube1.tube_image, ((self.model.tube1.x - self.model.mario.x + 150 + self.model.mario.width), self.model.tube1.y))
        self.screen.blit(self.model.tube2.tube_image, ((self.model.tube2.x - self.model.mario.x + 150 + self.model.mario.width), self.model.tube2.y))
        if self.model.goombas_Alive == 1:
            self.screen.blit(self.model.goomba.goomba_images[self.model.goomba.goomba_image_num], ((self.model.goomba.x - self.model.mario.x + 150), self.model.goomba.y))
        for i in range(len(self.model.fireballs)):
            if self.model.fireballs[i].used == 0:
                self.screen.blit(self.model.fireballs[i].fireball_image, ((self.model.fireballs[i].x - self.model.mario.x + 150) , self.model.fireballs[i].y)) 
        self.screen.blit(self.ground_image, (0, 400))
        self.screen.blit(self.ground_image, (400, 400))
        pygame.display.flip()

class Controller():
    def __init__(self, model):
        self.model = model
        self.keep_going = True

    def update(self):
        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    self.keep_going = False
        #Key pressed commands
        keys = pygame.key.get_pressed()
        if keys[K_LEFT]:
            self.model.mario.savePastPos()
            self.model.mario.movement(1)
            self.model.mario.toggle_Animate = 1
        if keys[K_RIGHT]:
            self.model.mario.savePastPos()
            self.model.mario.movement(2)
            self.model.mario.toggle_Animate = 1
        if not(keys[K_LEFT] or keys[K_RIGHT]):
            self.model.mario.movement(0)
            self.model.mario.toggle_Animate = 0
        if keys[K_SPACE]:
            if self.model.mario.jump_Hight < 5:
                self.model.mario.jump_Hight += 1
        else:
            if self.model.mario.jump_Hight > 0:
                self.model.mario.toggle_Jump = 1
            else:
                self.model.mario.toggle_Jump = 0
                self.model.mario.jump_Hight = 0
        if (keys[K_RCTRL] or keys[K_LCTRL]):
            self.model.fireballATK()
        

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
print("Goodbye")
