import React, { useState, useEffect } from 'react'
import { Grid, Paper, Typography, Box } from '@mui/material'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import ArchitectureDiagram from '../components/ArchitectureDiagram'

interface ThreatData {
  timestamp: string
  threatLevel: number
}

const Dashboard: React.FC = () => {
  const [threatData, setThreatData] = useState<ThreatData[]>([])

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:8765')

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data)
      const now = new Date()
      const threatLevel = data.threat_prob ? data.threat_prob * 100 : 0

      setThreatData(prev => [
        ...prev,
        {
          timestamp: now.toLocaleTimeString(),
          threatLevel: threatLevel
        }
      ].slice(-20))
    }

    ws.onerror = (error) => {
      console.error('WebSocket error:', error)
    }

    return () => ws.close()
  }, [])

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper sx={{ p: 2 }}>
          <Typography variant="h6" gutterBottom>
            Real-time Threat Level
          </Typography>
          <Box sx={{ height: 300 }}>
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={threatData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="timestamp" />
                <YAxis />
                <Tooltip />
                <Line
                  type="monotone"
                  dataKey="threatLevel"
                  stroke="#8884d8"
                  strokeWidth={2}
                  dot={false}
                />
              </LineChart>
            </ResponsiveContainer>
          </Box>
        </Paper>
      </Grid>

      <Grid item xs={12} md={6}>
        <Paper sx={{ p: 2 }}>
          <Typography variant="h6" gutterBottom>
            Active Threats
          </Typography>
          {/* TODO: Add active threats list component */}
        </Paper>
      </Grid>

      <Grid item xs={12}>
        <Paper sx={{ p: 2 }}>
          <Typography variant="h6" gutterBottom>
            System Architecture
          </Typography>
          <ArchitectureDiagram />
        </Paper>
      </Grid>
      {/* TODO: Add system status component */}
    </Grid>
  )
}

export default Dashboard