# Mini Tibia - Java Edition

Um jogo estilo Tibia com mapas em mosaicos (tiles), inimigos, NPCs e detecção de colisão.

## 🎮 Como Rodar

### Opção 1: Terminal

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/KrizFish/mini-tibia-java.git
   cd mini-tibia-java
   ```

2. **Compile:**
   ```bash
   javac src/*.java -d bin
   ```

3. **Execute:**
   ```bash
   java -cp bin Game
   ```

### Opção 2: IDE (IntelliJ/Eclipse)

1. Abra a pasta como projeto
2. Clique em `Game.java`
3. Clique em "Run" (Shift + F10 no IntelliJ)

## 🎯 Controles

| Tecla | Ação |
|-------|------|
| **Setas** | Mover para cima, baixo, esquerda, direita |
| **WASD** | Alternativa para mover |
| **ESC** | Sair do jogo |

## 📋 Recursos

- ✅ Mapas baseados em mosaicos (32x32px)
- ✅ Inimigos com IA (perseguem quando perto)
- ✅ NPCs para interagir
- ✅ Detecção de colisão AABB (retângulo x retângulo)
- ✅ Sistema de vida e dano
- ✅ Barra de HP visual
- ✅ 3 tipos de inimigos diferentes

## 📁 Estrutura do Projeto

```
src/
├── Game.java           # Main - inicia tudo
├── GamePanel.java      # Painel do jogo
├── Player.java         # Jogador (azul)
├── Enemy.java          # Inimigos (vermelho)
├── NPC.java            # NPCs (amarelo)
├── Entity.java         # Classe base
└── TileMap.java        # Mapa com tiles
```

## 🗺️ Como funciona Tiles (Mosaicos)?

Cada **tile** é um quadrado de 32x32 pixels. O mapa é uma matriz:

```
┌─────┬─────┬─────┐
│  0  │  0  │  1  │  0 = Grama (passável)
├─────┼─────┼─────┤  1 = Água (bloqueado)
│  0  │  2  │  0  │  2 = Árvore (bloqueado)
├─────┼─────┼─────┤
│  1  │  0  │  0  │
└─────┴─────┴─────┘
```

**Conversão de coordenadas:**
- Posição em pixels: `(x, y)`
- Posição em tile: `(x/32, y/32)`
- Volta para pixels: `(tileX*32, tileY*32)`

## 🎮 Mecânicas de Jogo

### Colisão
```java
// Detecta se dois retângulos se tocam (AABB)
if (player.colidesCom(inimigo)) {
    player.levarDano(10);
}
```

### IA dos Inimigos
1. Se player está **perto** (distância < 200px) → **persegue**
2. Se player está **longe** → **anda aleatoriamente**

### NPCs
- Personagens amarelos fixos no mapa
- Exibem mensagens no console quando você se aproxima

## 📊 Próximas Features

- [ ] Sistema de inventário
- [ ] Múltiplas camadas de mapa
- [ ] Teletransporte entre cidades
- [ ] Sistema de magia/habilidades
- [ ] Quest system
- [ ] Save/Load game
- [ ] Multiplayer (servidor)

## 🔧 Requisitos

- **Java 11+**
- **Sem dependências externas** (usa apenas Swing)

## 📝 Licença

MIT License - Sinta-se livre para modificar!

---

**Feito por KrizFish** 🎮
