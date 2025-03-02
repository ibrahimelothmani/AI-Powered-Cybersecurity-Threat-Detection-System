import React, { useState, useEffect } from 'react'
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Chip,
  Box
} from '@mui/material'

interface Threat {
  id: string
  timestamp: string
  type: string
  severity: 'low' | 'medium' | 'high'
  source: string
  destination: string
  details: string
}

const Threats: React.FC = () => {
  const [threats, setThreats] = useState<Threat[]>([])

  useEffect(() => {
    // TODO: Replace with actual API call
    const mockThreats: Threat[] = [
      {
        id: '1',
        timestamp: new Date().toISOString(),
        type: 'Port Scan',
        severity: 'high',
        source: '192.168.1.100',
        destination: '192.168.1.1',
        details: 'Multiple connection attempts detected'
      },
      {
        id: '2',
        timestamp: new Date().toISOString(),
        type: 'Suspicious Traffic',
        severity: 'medium',
        source: '192.168.1.150',
        destination: '10.0.0.1',
        details: 'Unusual data transfer pattern'
      }
    ]

    setThreats(mockThreats)
  }, [])

  const getSeverityColor = (severity: string) => {
    switch (severity) {
      case 'high':
        return 'error'
      case 'medium':
        return 'warning'
      case 'low':
        return 'info'
      default:
        return 'default'
    }
  }

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Active Threats
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Timestamp</TableCell>
              <TableCell>Type</TableCell>
              <TableCell>Severity</TableCell>
              <TableCell>Source</TableCell>
              <TableCell>Destination</TableCell>
              <TableCell>Details</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {threats.map((threat) => (
              <TableRow key={threat.id}>
                <TableCell>{new Date(threat.timestamp).toLocaleString()}</TableCell>
                <TableCell>{threat.type}</TableCell>
                <TableCell>
                  <Chip
                    label={threat.severity.toUpperCase()}
                    color={getSeverityColor(threat.severity)}
                    size="small"
                  />
                </TableCell>
                <TableCell>{threat.source}</TableCell>
                <TableCell>{threat.destination}</TableCell>
                <TableCell>{threat.details}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  )
}

export default Threats