-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 10.129.76.12
-- Tempo de geração: 01/06/2024 às 21:37
-- Versão do servidor: 5.6.26-log
-- Versão do PHP: 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `cityserver`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `Chats`
--

CREATE TABLE `Chats` (
  `ChatID` int(11) NOT NULL,
  `AccountID` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Msg` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tabela de Chats';

--
-- Despejando dados para a tabela `Chats`
--

INSERT INTO `Chats` (`ChatID`, `AccountID`, `Name`, `Msg`) VALUES
(1, '123322', 'ADMIN', 'Bem-Vindo'),
(2, '123322', 'ADMIN', 'Utilize o site para novas versoes'),
(3, '123322', 'ADMIN', 'tenha um bom jogo'),
(24, '506943', 'Gui', 'teste1'),
(25, '506943', 'Gui', 'teste2'),
(26, '506943', 'Gui', 'teste3'),
(27, '506943', 'Gui', 'teste4'),
(28, '506943', 'Gui', 'teste5'),
(29, '506943', 'Gui', 'teste6'),
(30, '506943', 'Gui', 'teste7'),
(31, '506943', 'Gui', 'teste8');

-- --------------------------------------------------------

--
-- Estrutura para tabela `Mobs`
--

CREATE TABLE `Mobs` (
  `MobSyncID` int(11) NOT NULL,
  `MobIDA` varchar(150) NOT NULL,
  `MobHpA` varchar(25) NOT NULL,
  `MobMpA` varchar(25) NOT NULL,
  `MobPosXA` varchar(30) NOT NULL,
  `MobPosYA` varchar(30) NOT NULL,
  `MobTargetA` varchar(50) NOT NULL,
  `MobDeadA` varchar(10) NOT NULL,
  `MobTimeDeadA` varchar(20) NOT NULL,
  `MobIDB` varchar(150) NOT NULL,
  `MobHpB` varchar(25) NOT NULL,
  `MobMpB` varchar(25) NOT NULL,
  `MobPosXB` varchar(30) NOT NULL,
  `MobPosYB` varchar(30) NOT NULL,
  `MobTargetB` varchar(50) NOT NULL,
  `MobDeadB` varchar(10) NOT NULL,
  `MobTimeDeadB` varchar(20) NOT NULL,
  `MobIDC` varchar(150) NOT NULL,
  `MobHpC` varchar(25) NOT NULL,
  `MobMpC` varchar(25) NOT NULL,
  `MobPosXC` varchar(30) NOT NULL,
  `MobPosYC` varchar(30) NOT NULL,
  `MobTargetC` varchar(50) NOT NULL,
  `MobDeadC` varchar(10) NOT NULL,
  `MobTimeDeadC` varchar(20) NOT NULL,
  `MobIDD` varchar(150) NOT NULL,
  `MobHpD` varchar(50) NOT NULL,
  `MobMpD` varchar(50) NOT NULL,
  `MobPosXD` varchar(50) NOT NULL,
  `MobPosYD` varchar(50) NOT NULL,
  `MobTargetD` varchar(50) NOT NULL,
  `MobDeadD` varchar(50) NOT NULL,
  `MobTimeDeadD` varchar(50) NOT NULL,
  `Map` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `Mobs`
--

INSERT INTO `Mobs` (`MobSyncID`, `MobIDA`, `MobHpA`, `MobMpA`, `MobPosXA`, `MobPosYA`, `MobTargetA`, `MobDeadA`, `MobTimeDeadA`, `MobIDB`, `MobHpB`, `MobMpB`, `MobPosXB`, `MobPosYB`, `MobTargetB`, `MobDeadB`, `MobTimeDeadB`, `MobIDC`, `MobHpC`, `MobMpC`, `MobPosXC`, `MobPosYC`, `MobTargetC`, `MobDeadC`, `MobTimeDeadC`, `MobIDD`, `MobHpD`, `MobMpD`, `MobPosXD`, `MobPosYD`, `MobTargetD`, `MobDeadD`, `MobTimeDeadD`, `Map`) VALUES
(3, 'SlimeA', '50', '30', '3.9899986', '-5.3200026', 'none', 'no', '0', 'SlimeB', '50', '30', '29.139938', '11.329994', 'none', 'no', '0', 'OikPlantA', '80', '30', '-40.109978', '-59.13994', 'none', 'no', '0', 'PoroA', '130', '30', '20', '20', 'none', 'no', '0', 'Sewers'),
(7, 'PoyoA', '420', '50', '0', '0', 'none', 'no', '250', 'PoyoB', '420', '50', '13', '15', 'none', 'no', '250', 'AranoidA', '620', '50', '-35', '-45', 'none', 'none', '250', 'SharkA', '890', '50', '-35', '-50', 'none', 'no', '250', 'Watercave');

-- --------------------------------------------------------

--
-- Estrutura para tabela `Reports`
--

CREATE TABLE `Reports` (
  `ReportID` int(11) NOT NULL,
  `Descricao` varchar(255) NOT NULL,
  `Tipo` varchar(255) NOT NULL,
  `Situacao` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `Reports`
--

INSERT INTO `Reports` (`ReportID`, `Descricao`, `Tipo`, `Situacao`) VALUES
(3, 'Lolo', 'Bem vindo', 'Pendente'),
(4, 'Lala', '', 'Pendente'),
(5, '', '', 'Pendente'),
(6, '', '', 'Pendente');

-- --------------------------------------------------------

--
-- Estrutura para tabela `SaveData`
--

CREATE TABLE `SaveData` (
  `SaveDataID` int(11) NOT NULL,
  `AccountID` varchar(15) DEFAULT NULL,
  `Name` varchar(15) DEFAULT NULL,
  `Sex` varchar(15) DEFAULT NULL,
  `Level` varchar(15) DEFAULT NULL,
  `Map` varchar(15) DEFAULT NULL,
  `Exp` varchar(15) DEFAULT NULL,
  `Job` varchar(15) DEFAULT NULL,
  `Hp` varchar(15) DEFAULT NULL,
  `Mp` varchar(15) DEFAULT NULL,
  `Money` varchar(15) DEFAULT NULL,
  `HpMax` varchar(15) DEFAULT NULL,
  `MpMax` varchar(15) DEFAULT NULL,
  `regenTime` varchar(15) DEFAULT NULL,
  `regenTimeMax` varchar(15) DEFAULT NULL,
  `PosX` varchar(15) DEFAULT NULL,
  `PosY` varchar(15) DEFAULT NULL,
  `Walk` varchar(15) DEFAULT NULL,
  `Frame` varchar(15) DEFAULT NULL,
  `Target` varchar(15) DEFAULT NULL,
  `AtkTimer` varchar(15) DEFAULT NULL,
  `AtkTimerMax` varchar(15) DEFAULT NULL,
  `Casting` varchar(15) DEFAULT NULL,
  `Atk` varchar(15) DEFAULT NULL,
  `Def` varchar(15) DEFAULT NULL,
  `Evasion` varchar(15) DEFAULT NULL,
  `Side` varchar(15) DEFAULT NULL,
  `SetPlayer` varchar(15) DEFAULT NULL,
  `Hair` varchar(15) DEFAULT NULL,
  `Color` varchar(15) DEFAULT NULL,
  `Hat` varchar(15) DEFAULT NULL,
  `Weapon` varchar(15) DEFAULT NULL,
  `Crystal1` varchar(15) DEFAULT NULL,
  `Crystal2` varchar(15) DEFAULT NULL,
  `Crystal3` varchar(15) DEFAULT NULL,
  `Crystal4` varchar(15) DEFAULT NULL,
  `Crystal5` varchar(15) DEFAULT NULL,
  `StatusPoint` varchar(15) DEFAULT NULL,
  `Str` varchar(15) DEFAULT NULL,
  `Agi` varchar(15) DEFAULT NULL,
  `Vit` varchar(15) DEFAULT NULL,
  `Dex` varchar(15) DEFAULT NULL,
  `Wis` varchar(15) DEFAULT NULL,
  `Luk` varchar(15) DEFAULT NULL,
  `Res` varchar(15) DEFAULT NULL,
  `Stamina` varchar(15) DEFAULT NULL,
  `StaminaMax` varchar(15) DEFAULT NULL,
  `StaminaTimer` varchar(15) DEFAULT NULL,
  `Itens` varchar(255) DEFAULT NULL,
  `Quests` varchar(255) DEFAULT NULL,
  `hotkey1` varchar(15) DEFAULT NULL,
  `hotkey2` varchar(15) DEFAULT NULL,
  `buffA` varchar(15) DEFAULT NULL,
  `BuffTimeA` varchar(15) DEFAULT NULL,
  `buffB` varchar(15) DEFAULT NULL,
  `BuffTimeB` varchar(15) DEFAULT NULL,
  `buffC` varchar(15) DEFAULT NULL,
  `BuffTimeC` varchar(15) DEFAULT NULL,
  `party` varchar(15) DEFAULT NULL,
  `playerInBattle` varchar(15) DEFAULT NULL,
  `playerInAttack` varchar(15) DEFAULT NULL,
  `playerInCast` varchar(15) DEFAULT NULL,
  `pet` varchar(20) NOT NULL,
  `pethungry` varchar(10) NOT NULL,
  `petcare` varchar(10) NOT NULL,
  `petTraining` varchar(10) NOT NULL,
  `petBath` varchar(10) NOT NULL,
  `petLevel` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `Sync`
--

CREATE TABLE `Sync` (
  `AccountID` int(11) NOT NULL,
  `name` varchar(15) NOT NULL,
  `level` varchar(15) NOT NULL,
  `map` varchar(15) NOT NULL,
  `hp` varchar(15) NOT NULL,
  `mp` varchar(15) NOT NULL,
  `posX` varchar(15) NOT NULL,
  `posY` varchar(15) NOT NULL,
  `walk` varchar(15) NOT NULL,
  `weapon` varchar(15) NOT NULL,
  `frame` varchar(15) NOT NULL,
  `syncPlayerMob` varchar(15) NOT NULL,
  `setUpper` varchar(15) NOT NULL,
  `setBottom` varchar(15) NOT NULL,
  `setFooter` varchar(15) NOT NULL,
  `hair` varchar(15) NOT NULL,
  `sex` varchar(15) NOT NULL,
  `color` varchar(15) NOT NULL,
  `hat` varchar(15) NOT NULL,
  `side` varchar(15) NOT NULL,
  `job` varchar(15) NOT NULL,
  `playerInBattle` varchar(15) NOT NULL,
  `playerInAttack` varchar(15) NOT NULL,
  `playerInCast` varchar(15) NOT NULL,
  `playerSit` varchar(15) NOT NULL,
  `party` varchar(15) NOT NULL,
  `expShared` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura para tabela `VersionControl`
--

CREATE TABLE `VersionControl` (
  `versionID` int(11) NOT NULL,
  `selector` varchar(20) NOT NULL,
  `descricao` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Despejando dados para a tabela `VersionControl`
--

INSERT INTO `VersionControl` (`versionID`, `selector`, `descricao`) VALUES
(1, 'KeyCheck', '1A');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `Chats`
--
ALTER TABLE `Chats`
  ADD PRIMARY KEY (`ChatID`);

--
-- Índices de tabela `Mobs`
--
ALTER TABLE `Mobs`
  ADD PRIMARY KEY (`MobSyncID`);

--
-- Índices de tabela `Reports`
--
ALTER TABLE `Reports`
  ADD PRIMARY KEY (`ReportID`);

--
-- Índices de tabela `SaveData`
--
ALTER TABLE `SaveData`
  ADD PRIMARY KEY (`SaveDataID`);

--
-- Índices de tabela `Sync`
--
ALTER TABLE `Sync`
  ADD PRIMARY KEY (`AccountID`),
  ADD UNIQUE KEY `AccountID` (`AccountID`);

--
-- Índices de tabela `VersionControl`
--
ALTER TABLE `VersionControl`
  ADD PRIMARY KEY (`versionID`),
  ADD UNIQUE KEY `selector` (`selector`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `Chats`
--
ALTER TABLE `Chats`
  MODIFY `ChatID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de tabela `Mobs`
--
ALTER TABLE `Mobs`
  MODIFY `MobSyncID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `Reports`
--
ALTER TABLE `Reports`
  MODIFY `ReportID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `SaveData`
--
ALTER TABLE `SaveData`
  MODIFY `SaveDataID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `Sync`
--
ALTER TABLE `Sync`
  MODIFY `AccountID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=521;

--
-- AUTO_INCREMENT de tabela `VersionControl`
--
ALTER TABLE `VersionControl`
  MODIFY `versionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
